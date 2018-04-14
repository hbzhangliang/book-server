package cn.com.flaginfo.platform.terminal.controller;


import cn.com.flaginfo.platform.terminal.services.UserApi;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Validated
@Controller
@RequestMapping(value = "/user",produces = "application/json; charset=utf-8")
public class UserController {


    @RequestMapping(value = "/user_info")
    @ResponseBody
    public Object getUserInfo(@RequestBody Map<String,Object> map){
        String api=String.valueOf(map.get("api"));
        JSONObject jsonObject=JSONObject.parseObject(JSONObject.toJSONString(map.get("json")));
        return userApi.userInfo(api,jsonObject);
    }


    @Autowired
    private UserApi userApi;

}
