package cn.com.flaginfo.platform.terminal.services;

import cn.com.flaginfo.platform.terminal.services.vo.Group;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

public interface TryApi {


    Future<String> asynGetInfo();



    //每隔两分钟读取一遍缓存数据
    Group getMsg(String groupId,String mobile);


    //发送msg的处理
    void sendMsg(String groupId,String mobile,String content);



    //创建群聊
    String createGroup(String mobile, String groupName, List<Map<String,String>> contacts);


    Boolean quitGroup(String mobile,String groupId);


    /**
     * 获取group列表
     * @param mobile
     * @return
     */
    List<Group>  getGroupList(String mobile);


    /**
     * 每隔10秒钟获取一次，看有没有更新数据
     * @param mobile
     * @return
     */
    List<Group> getGroupNews(String mobile);


}
