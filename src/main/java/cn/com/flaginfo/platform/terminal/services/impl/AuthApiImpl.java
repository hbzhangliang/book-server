package cn.com.flaginfo.platform.terminal.services.impl;


import cn.com.flaginfo.platform.terminal.services.AuthApi;
import cn.com.flaginfo.platform.terminal.utils.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class AuthApiImpl implements AuthApi {

    private static final Logger log = LoggerFactory.getLogger(AuthApiImpl.class);

    /**
     * 检测并存入缓存 并写到cookie
     * @param mobile
     * @param pwd
     * @return
     */
    @Override
    public JSONObject check(HttpServletResponse response, String mobile, String pwd) {
        String redisConf="User_Token";
        String api="user_check";
        JSONObject params=new JSONObject(5);
        params.put("account",mobile);
        params.put("password",pwd);
        params.put("type","1");
        JSONObject result=platformHelper.postReq(api,params);

        String returnCode=result.get("returnCode").toString();
        //返回数据成功
        if(ComonParas.returnCode.contains(returnCode)){
            JSONObject user=result.getJSONObject("userInfo");
            String userId=user.getString("userId");

            //存入缓存
            String token=UtilHelper.contacsString(redisConf,userId);
            redisUtils.setObj(token,user,redisConf);

            //写入cookie
            String encodeToken= CodeUtils.getEncryptedToken(token);
            CookieUtils.writeCookie(response,redisConf,encodeToken);

            return user;
        }
        else {
            log.error("api is [{}],request error",api);
            return null;
        }
    }



    @Override
    public JSONArray getUserCorps(String userId){
        String redisConf="User_CorpList";
        Object redisValue=redisUtils.getObj(redisConf+userId);
        if(redisValue!=null){
              return (JSONArray)redisValue;
//            return JSONArray.parseArray(JSONObject.toJSONString(redisValue));
        }

        String api="get_sp_list";
        JSONObject params=new JSONObject(5);
        params.put("userId",userId);
        JSONObject result=platformHelper.postReq(api,params);

        String returnCode=result.get("returnCode").toString();
        //返回数据成功
        if(ComonParas.returnCode.contains(returnCode)){
            JSONArray corps=result.getJSONArray("spInfoList");
            corps=this.checkCorpStatus(corps);

            //存入缓存
            redisUtils.setObj(UtilHelper.contacsString(redisConf,userId),corps,redisConf);

            return corps;
        }
        else {
            log.error("api is [{}],request error",api);
            return null;
        }
    }



    private JSONArray checkCorpStatus(JSONArray array){
        JSONArray result=new JSONArray(10);
        if(array!=null&&array.size()>0){
            for(int i=0;i<array.size();i++){
                JSONObject p = array.getJSONObject(i);
                String status=p.getString("yxtStatus");
                if(StringUtils.isNotBlank(status)&&status.equals("1")){
                    result.add(p);
                }
            }
        }
        return result;
    }


    @Override
    public JSONObject userAndCorpsByToken(String token) {
        JSONObject user=(JSONObject) redisUtils.getObj(token);
        JSONArray corps=this.getUserCorps(user.getString("userId"));
        JSONObject result=new JSONObject(5);
        result.put("user",user);
        result.put("corps",corps);
        return result;
    }


    @Override
    public JSONObject getMemberInfo(HttpServletResponse response,String userId, String corpId) {
        String redisConf="Member_Token";
//        String redisKey=UtilHelper.contacsString(redisConf,userId,corpId);
//        Object redisValue=redisUtils.getObj(redisKey);
//        if(redisValue!=null){
//            return (JSONObject) redisValue;
//        }

        String api="user_member_get_by_userid";
        JSONObject params=new JSONObject(5);
        params.put("userId",userId);
        params.put("spId",corpId);
        JSONObject result=platformHelper.postReq(api,params);

        String returnCode=result.get("returnCode").toString();
        //返回数据成功
        if(ComonParas.returnCode.contains(returnCode)){
            JSONObject memberInfo=result.getJSONObject("member");

            //添加了用户id 企业id
            memberInfo.put("strUserId",userId);
            memberInfo.put("strCorpId",corpId);
            //存入缓存
//            redisUtils.setObj(redisKey,memberInfo,redisConf);

            //根据memberId 再存一份
            String memberId=memberInfo.getString("id");
            String pk=UtilHelper.contacsString(redisConf,memberId);
            redisUtils.setObj(pk,memberInfo,redisConf);

            //写入cookie
            String encodeToken= CodeUtils.getEncryptedToken(pk);
            CookieUtils.writeCookie(response,redisConf,encodeToken);

            return memberInfo;
        }
        else {
            log.error("api is [{}],request error",api);
            return null;
        }
    }


    /**
     * 清除缓存   清除cookie
     * @param userToken
     * @param memberToken
     */
    @Override
    public Boolean logout(HttpServletResponse response,String userToken, String memberToken) {
        try {
            String redisUser="User_Token";
            String redisMember="Member_Token";

            redisUtils.delKeys(userToken, memberToken);
            CookieUtils.writeCookie(response, redisUser, null);
            CookieUtils.writeCookie(response, redisMember, null);

            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public JSONObject getMemberInfo(String memberId) {
        return (JSONObject)redisUtils.getObj(memberId);
    }


    @Override
    public JSONArray listAppsByCorpId(String corpId) {
        String redisKey="Corp_Apps";
        String key=UtilHelper.contacsString(redisKey,corpId);
        Object redisValue=redisUtils.getObj(key);
        if(redisValue!=null){
            return (JSONArray)redisValue;
        }

        String api="sp_current_app";
        JSONObject params=new JSONObject(5);
        params.put("spId",corpId);
        JSONObject result=platformHelper.postReq(api,params);

        String returnCode=result.get("returnCode").toString();
        //返回数据成功
        if(ComonParas.returnCode.contains(returnCode)){
            JSONArray apps=result.getJSONArray("list");

            //存入缓存
            redisUtils.setObj(key,apps,redisKey);
            return apps;
        }
        return null;
    }


    @Override
    public String getAppAuthUrl(String url,String redisMember) {
        String authKey=this.saveOAuthInfo(redisMember);
        url += (StringUtils.isNotBlank(url) ? "&" : "?") +
                "__mId=" + authKey + "&__h=" + AesAppUtils.encode(authKey, host);
        log.info("Gen Biz Visit URL [{}].", url);
        return url;
    }



    private  String saveOAuthInfo(String memberId) {
        String redisKey="App_Expri";
        String oauthKey = AesAppUtils.genKey();
        log.info("saveOAuthInfo memberId:[{}]", memberId);
        JSONObject map=this.getMemberInfo(memberId);

        // expire after 8 minutes
        redisUtils.setObj(oauthKey,map,redisKey);
        return oauthKey;
    }


    @Override
    public String getContactsId(String corpId) {

        String redisKey="Corp_ContactsId";
        String key=UtilHelper.contacsString(redisKey,corpId);
        Object redisValue=redisUtils.getObj(key);
        if(redisValue!=null){
            return (String) redisValue;
        }

        String api="contacts_conf_list";
        JSONObject params=new JSONObject(5);
        params.put("spId",corpId);
        params.put("type","3");
        JSONObject result=platformHelper.postReq(api,params);
        String returnCode=result.get("returnCode").toString();
        //返回数据成功
        if(ComonParas.returnCode.contains(returnCode)){
            //写入缓存
            JSONArray jsonArray=result.getJSONArray("list");
            if(jsonArray!=null&&jsonArray.size()>0){
                String contactsId=jsonArray.getJSONObject(0).getString("contactsId");
                redisUtils.setObj(key,contactsId,redisKey);
                return contactsId;
            }
        }
        return null;
    }


    /**
     *  {
     "id": "16101111092317759478",
     "createTime": "2016-10-11 11:09:23",
     "groupLevel": "1",
     "remark": "",
     "userId": "16101015273617726867",
     "name": "研发中心",
     "seq": "1",
     "contactsId": "16101015273617726868",
     "pid": "0",
     "spId": "16101015273617696727"
     },\\





     {
     "_id":{
     "timestamp":1476155452,
     "machineIdentifier":15616262,
     "processIdentifier":3764,
     "counter":4882136,
     "timeSecond":1476155452,
     "date":"2016-10-11 11:10:52",
     "time":1476155452000
     },
     "groupList":[{
     "id":"16101111105117759555",
     "top":"0"
     }],
     "name":"张春卉",
     "mdn":"15801702324",
     "id":"57fc583cee49060eb44a7ed8"
     },
     * @param corpId
     * @param userId
     * @param groupId
     * @return
     */
    @Override
    public JSONArray getContactsList(String corpId, String userId,String groupId) {
        String contactsId=this.getContactsId(corpId);
        JSONArray groupList=this.getContactsGroupList(corpId,userId,contactsId);
        JSONArray memberList=this.getContactsMemberList(corpId,userId,contactsId);

        JSONArray result=new JSONArray();

        if(groupList!=null&&groupList.size()>0){
           for(Object groupItem:groupList){
               JSONObject p=(JSONObject) groupItem;

               if(p.getString("pid").equals(groupId)){
                   result.add(p);
               }

           }
        }

        if(memberList!=null&&memberList.size()>0){
            for(Object memberItem:memberList){
                JSONObject p=(JSONObject)memberItem;
                JSONArray tmp=p.getJSONArray("groupList");
                Boolean flag=false;
                if(tmp!=null&&tmp.size()>0){
                    for(Object q:tmp){
                        JSONObject m=(JSONObject)q;
                        if(m.getString("id").equals(groupId)){
                            flag=true;
                            break;
                        }
                    }
                }
                if(flag){
                    result.add(p);
                }
            }
        }
        return result;
    }


    /**
     * {
     "spId":"16101015273617696727",
     "userId":"16050517311210007249",
     "contactsId":"16101015273617726868",
     "isTotal":"1"
     }
     * @param corpId
     * @param userId
     * @param contactsId
     * @return
     */
    @Override
    public JSONArray getContactsGroupList(String corpId, String userId, String contactsId) {
        String redisKey="Corp_GROUPLIST";
        String key=UtilHelper.contacsString(redisKey,corpId,userId);
        Object redisValue=redisUtils.getObj(key);
        if(redisValue!=null){
            return (JSONArray) redisValue;
        }

        String api="contacts_group_list";
        JSONObject params=new JSONObject(5);
        params.put("spId",corpId);
        params.put("userId",userId);
        params.put("contactsId",contactsId);
        params.put("isTotal","1");
        JSONObject result=platformHelper.postReq(api,params);
        String returnCode=result.get("returnCode").toString();
        //返回数据成功
        if(ComonParas.returnCode.contains(returnCode)){
            //写入缓存
            JSONArray jsonArray=result.getJSONArray("list");
            if(jsonArray!=null&&jsonArray.size()>0){
                redisUtils.setObj(key,jsonArray,redisKey);
                return jsonArray;
            }
        }
        return null;
    }


    @Override
    public JSONArray getContactsMemberList(String corpId, String userId, String contactsId) {
        String redisKey="Corp_MEMBERLIST";
        String key=UtilHelper.contacsString(redisKey,corpId,userId);
        Object redisValue=redisUtils.getObj(key);
        if(redisValue!=null){
            return (JSONArray) redisValue;
        }

        String api="contacts_member_list_all";
        JSONObject params=new JSONObject(5);
        params.put("spId",corpId);
        params.put("userId",userId);
        params.put("contactsId",contactsId);

        List<String> parsList=new ArrayList<>(5);
        parsList.add("id");
        parsList.add("name");
        parsList.add("mdn");
        parsList.add("groupList");
        params.put("attrList",parsList);

        JSONObject result=platformHelper.postReq(api,params);
        String returnCode=result.get("returnCode").toString();
        //返回数据成功
        if(ComonParas.returnCode.contains(returnCode)){
            //写入缓存
            JSONArray jsonArray=result.getJSONArray("list");
            if(jsonArray!=null&&jsonArray.size()>0){
                redisUtils.setObj(key,jsonArray,redisKey);
                return jsonArray;
            }
        }
        return null;
    }


    @Override
    public JSONObject getMsgByGroupId(String groupId) {



        return null;
    }




    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;


    @Value("${umsapp.base.path}")
    private String host;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private PlatformHelper platformHelper;
}
