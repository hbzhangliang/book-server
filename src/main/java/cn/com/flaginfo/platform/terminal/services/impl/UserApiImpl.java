package cn.com.flaginfo.platform.terminal.services.impl;

import cn.com.flaginfo.platform.api.common.base.BaseResponse;
import cn.com.flaginfo.platform.terminal.services.UserApi;
import cn.com.flaginfo.platform.terminal.utils.ComonParas;
import cn.com.flaginfo.platform.terminal.utils.PlatformHelper;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserApiImpl implements UserApi {

    private static final Logger log = LoggerFactory.getLogger(UserApiImpl.class);

    @Autowired
    private PlatformHelper platformHelper;

    @Override
    public BaseResponse userInfo(String api, JSONObject jsonObject) {
        JSONObject result=platformHelper.postReq(api,jsonObject);
        return new BaseResponse("获取数据成功",result);
    }


    @Override
    public Boolean changePwd(String userId, String password, String newPassword) {
        String api="password_update";
        JSONObject params=new JSONObject(5);
        params.put("userId",userId);
        params.put("password",password);
        params.put("newPassword",newPassword);
        JSONObject result=platformHelper.postReq(api,params);
        String returnCode=result.get("returnCode").toString();
        //返回数据成功
        if(ComonParas.returnCode.contains(returnCode)){
            log.info("post api [{}] success",api);
            return true;
        }
        log.error("post api [{}] error",api);
        return false;
    }
}
