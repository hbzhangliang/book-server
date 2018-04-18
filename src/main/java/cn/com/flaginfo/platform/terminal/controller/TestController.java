package cn.com.flaginfo.platform.terminal.controller;

import cn.com.flaginfo.platform.terminal.mongo.models.Tmp;
import cn.com.flaginfo.platform.terminal.mongo.repo.TmpRepo;
import cn.com.flaginfo.platform.terminal.mysql.entity.CoreUser;
import cn.com.flaginfo.platform.terminal.mysql.mapper.CoreUserMapper;
import cn.com.flaginfo.platform.terminal.services.AuthorApi;
import cn.com.flaginfo.platform.terminal.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by liang_zhang on 2017/9/25.
 */
@Controller
@RequestMapping("/test")
public class TestController {



    private static final Logger log = LoggerFactory.getLogger(TestController.class);



    @RequestMapping(value = "/1")
    public ModelAndView test1() {
        return new ModelAndView("config/main");
    }


    @RequestMapping(value = "/health")
    @ResponseBody
    public Object testHealth() {
        return "server is start";
    }

    @RequestMapping(value = "/mysql")
    @ResponseBody
    public Object testMysql() {
        CoreUser user = new CoreUser();
        String id= UUID.randomUUID().toString().replace("-","");
        user.setId(id);
        user.setAccount("abc");
        user.setRealName("zhangsan11111");
        user.setPwd("11111");
        user.setDepartId("-1");
        user.setUpdateTime(new Date());
        coreUserMapper.insert(user);

        return coreUserMapper.selectByExample(null);
    }


    @RequestMapping(value = "/redis")
    @ResponseBody
    public Object testRedis(@RequestParam String key,@RequestParam String value) {
        String redisTimeKey="COMMON";
        redisUtils.setObj(key,value,redisTimeKey);
        return redisUtils.getObj(key);

    }

    @RequestMapping(value = "/mongo")
    @ResponseBody
    public Object testMong() {
        Tmp tmp=new Tmp();
        tmp.setName("abc");
        tmp.setCode("code");
        tmpRepo.save(tmp);
        return tmpRepo.list();


    }



    @RequestMapping(value = "/author")
    @ResponseBody
    public Object author() {
        authorApi.generateAuthor();
        return null;
    }


    @RequestMapping(value = "/authorfilter")
    @ResponseBody
    public Object authorFilter() {
        authorApi.filterAuthor();
        return null;
    }



    @Autowired
    private AuthorApi authorApi;

//    @RequestMapping(value = "/6")
//    public ModelAndView test6() {
//        CoreUserExample example = new CoreUserExample();
//        example.createCriteria().andIdEqualTo("eeeeee");
//
//
//        CoreUser user = new CoreUser();
//        user.setId("eeeeee");
//        user.setAccount("abc");
//        user.setRealName("zhangsan11111");
//        user.setPwd("11111");
//        user.setDepartId("-1");
//
//        coreUserMapper.updateByExample(user, example);
//        return null;
//    }
//
//    @RequestMapping(value = "/7")
//    @ResponseBody
//    public Object test7(@RequestBody Map map) {
//        log.info("map is[{}]", map);
//        int i = Integer.valueOf(33333);
//        return i;
//    }
//
//    @RequestMapping(value = "/8")
//    @ResponseBody
//    public Object test8() {
//
//        double p=(330.0/290-1)*100;
//        DecimalFormat df = new DecimalFormat("#.0");
//        return df.format(p);
//    }
//
//
//    /**
//     * {"type":"1","spCode":"280217","account":"13888870856", "password":"123456"}
//     * @return
//     */
//    @RequestMapping(value = "/9")
//    @ResponseBody
//    public Object test9() {
//        JSONObject jb=new JSONObject();
//        jb.put("type","1");
//        jb.put("spCode","280217");
//        jb.put("account","13888870856");
//        jb.put("password","123456");
//        return platformHelper.postReq("account_check",jb);
//    }
//
//
//
//
//
//    @RequestMapping(value = "/10")
//    @ResponseBody
//    public Object test10(HttpServletResponse response,@RequestBody Map<String,String > map) {
//        String redisUser="User_Token";
//        String redisMember="Member_Token";
//        redisUtils.delKeys(map.get("id"));
//
//        CookieUtils.writeCookie(response, redisUser, null);
//        CookieUtils.writeCookie(response, redisMember, null);
//
//        return null;
//
//    }
//
//
//
//    @RequestMapping(value = "/11")
//    @ResponseBody
//    public Object test11(HttpServletResponse response,@RequestBody Map<String,String > map) {
//        redisUtils.delKeys(map.get("key"));
//        return null;
//
//    }
//
//
//    @RequestMapping(value = "/12")
//    @ResponseBody
//    public Object test12() throws Exception{
//        Future<String> result=tryApi.asynGetInfo();
//
//        Long start=System.currentTimeMillis();
//        log.info("test12 is [{}]",start);
//        log.info("1111");
//        while (!result.isDone()){
//            log.info("while start");
//            Thread.sleep(1000);
//            log.info("while end");
//        }
//        log.info("while end");
//        log.info("test12 end is [{}]",System.currentTimeMillis());
//        log.info("test12 spend is [{}]",System.currentTimeMillis()-start);
//        return result.get();
//    }
//
//
//    @RequestMapping(value = "/13")
//    @ResponseBody
//    public Object test13(@RequestParam String key,@RequestParam String value){
//        String redisTimeKey="COMMON";
//        redisUtils.setObj(key,value,redisTimeKey);
//        return redisUtils.getObj(key);
//    }


    @Autowired
    private PlatformHelper platformHelper;

    @Autowired
    private RedisUtils redisUtils;


    @Autowired
    private CoreUserMapper coreUserMapper;

    @Autowired
    private TmpRepo  tmpRepo;
}