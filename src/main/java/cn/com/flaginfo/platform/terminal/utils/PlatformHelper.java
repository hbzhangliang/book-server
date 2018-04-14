package cn.com.flaginfo.platform.terminal.utils;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class PlatformHelper {

    private static final PropertiesUtils redisProper = new PropertiesUtils("spring/redis-time-config.properties");

    private static final PropertiesUtils platformApiProper = new PropertiesUtils("spring/platform-api.properties");

    private static final Logger log = LoggerFactory.getLogger(PlatformHelper.class);



    public JSONObject postReq(String api,JSONObject jsonObject){
        String strUrl=platformApiProper.getPropertiesValue(api);
        JSONObject result=JSONHelper.httpPost(strUrl,jsonObject);
        log.info("requst for api[{}],url [{}], params [{}],result [{}]",api,strUrl,jsonObject,result);
        return result;
    }

}
