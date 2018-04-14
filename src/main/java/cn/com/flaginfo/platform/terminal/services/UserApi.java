package cn.com.flaginfo.platform.terminal.services;

import cn.com.flaginfo.platform.api.common.base.BaseResponse;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface UserApi {



    BaseResponse userInfo(String api, JSONObject jsonObject);



    Boolean changePwd(String userId,String password,String newPassword);

}
