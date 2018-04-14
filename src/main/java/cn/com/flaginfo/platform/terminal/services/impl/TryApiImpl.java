package cn.com.flaginfo.platform.terminal.services.impl;

import cn.com.flaginfo.platform.terminal.services.TryApi;
import cn.com.flaginfo.platform.terminal.services.vo.Group;
import cn.com.flaginfo.platform.terminal.utils.RedisUtils;
import cn.com.flaginfo.platform.terminal.utils.UtilHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;


@Service
public class TryApiImpl implements TryApi {
    private static final Logger log = LoggerFactory.getLogger(TryApiImpl.class);

    @Override
    public Future<String> asynGetInfo() {
        log.info("do it");
        Future<String> future = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return heavyWork();
            }
        });
        return future;
    }


    private String heavyWork() throws Exception{
        Long start=System.currentTimeMillis();
        log.info("asyn start");
        Thread.sleep(7000L);
        Long end=System.currentTimeMillis();
        log.info("asyn end");

        String result=String.format("start:%s,end:%s;spend:%s",start,end,(end-start));
        log.info(result);

        return result;
    }


    /**
     * 异步获取，如果 version有变化，直接返回，如果没有变化，异步处理
     * @param groupId
     * @param mobile
     * @return
     */
    @Override
    public Group getMsg(String groupId, String mobile) {
        Long sleepTime=1000L;
        Future<Group> future = executor.submit(new Callable<Group>() {
            @Override
            public Group call() throws Exception {
                return getSingleMsg(groupId,mobile);
            }
        });
        try {
            while (!future.isDone()) {
                Thread.sleep(sleepTime);
            }
            return future.get();
        }
        catch (Exception e){
            log.error("getMsg run error[{}]", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    private Group getSingleMsg(String groupId,String mobile){
        Long sleepTime=1000L;
        Long expireTime=60000L;
        //读取缓存数据
        String redisKey="Group_INFO";
        String key= UtilHelper.contacsString(redisKey,groupId);

        Group group=(Group) redisUtils.getObj(key);

        for(Map<String,String> item:group.getContactsMembers()){
            if(mobile.equals(item.get("mobile"))){
                String version=item.get("version");
                try {
                    while (group.getVersion().equals(version)) {
                        Thread.sleep(sleepTime);
                        expireTime-=sleepTime;
                        if(expireTime<0){
                            return null;
                        }
                    }
                    //将group的版本数据更改
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            item.put("version",group.getVersion().toString());
                            redisUtils.setObj(key,group,redisKey);
                        }
                    });
                    return group;
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    @Override
    public synchronized void sendMsg(String groupId,String mobile, String content) {
        //读取缓存数据
        String redisKey="Group_INFO";
        String key= UtilHelper.contacsString(redisKey,groupId);

        Group group=(Group) redisUtils.getObj(key);

        Map<String,String> contentMap=new HashMap<>(5);
        contentMap.put("mobile",mobile);
        contentMap.put("name",getNameByMobile(mobile,group));
        contentMap.put("content",content);
        contentMap.put("time",UtilHelper.getNowStrTime());

        if(group.getContents()==null){
            List<Map<String,String>> list=new ArrayList<>(100);
            list.add(contentMap);
            group.setContents(list);
        }
        else {
            group.getContents().add(contentMap);
        }
        group.setVersion(group.getVersion()+1);

//        for(Map<String,String> item:group.getContactsMembers()){
//            if(mobile.equals(item.get("mobile"))){
//                item.put("version",group.getVersion().toString());
//                break;
//            }
//        }
        redisUtils.setObj(key,group,redisKey);
    }



    private String getNameByMobile(String mobile,Group group){
        for(Map<String,String> item:group.getContactsMembers()) {
            if (mobile.equals(item.get("mobile"))) {
                return item.get("name");
            }
        }
        return null;
    }


    /**
     * create group and return the groupId
     *
     * contactId
     * mobile
     * name
     *
     *
     * @param
     * @param
     * @return
     */
    @Override
    public String createGroup(String mobile, String groupName, List<Map<String,String>> contacts) {
        Group group=new Group();
        String guid= UUID.randomUUID().toString().replace("-","");
        group.setId(guid);
        group.setName(groupName);
        for(Map<String,String> item:contacts){
            item.put("version","0");
        }
        group.setContactsMembers(contacts);
        group.setCreator(mobile);
        group.setCreateTime(new Date());

        //写入缓存
        String redisKey="Group_INFO";
        String key=UtilHelper.contacsString(redisKey,guid);
        redisUtils.setObj(key,group,redisKey);
        return guid;
    }


    @Override
    public Boolean quitGroup(String mobile, String groupId) {
        try{
            //写入缓存
            String redisKey="Group_INFO";
            String key=UtilHelper.contacsString(redisKey,groupId);
            Group group=(Group)redisUtils.getObj(key);
            List<Map<String,String>> members=new ArrayList<>(10);
            for(Map<String,String> item:group.getContactsMembers()){
                if(!item.get("mobile").equals(mobile)){
                    members.add(item);
                }
            }
            group.setContactsMembers(members);
            redisUtils.setObj(key,group,redisKey);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


    @Override
    public List<Group> getGroupList(String mobile) {
        String redisKey="Group_INFO";
        List<Group> result=new ArrayList<>(10);
        List<Object> list=redisUtils.getObjPatners(redisKey);
        if(list==null) return null;
        for(Object item:list){
            Group group=(Group)item;
            for(Map<String,String> p:group.getContactsMembers()){
                if(p.get("mobile").equals(mobile)){
                    result.add(group);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public List<Group>  getGroupNews(String mobile) {
        List<Group> list=this.getGroupList(mobile);
        List<Group> result=new ArrayList<>(20);
        try{
            Thread.sleep(10000L);//线程休眠10秒
        }
        catch (Exception e){
            e.printStackTrace();
        }
        for(Group item:list){
            Group p=this.getMsg(item.getId(),mobile);
            if(p!=null){
                result.add(p);
            }
        }

        return result;
    }

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ThreadPoolTaskExecutor executor;

}
