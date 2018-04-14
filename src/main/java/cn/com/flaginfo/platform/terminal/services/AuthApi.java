package cn.com.flaginfo.platform.terminal.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletResponse;

public interface AuthApi {

    JSONObject check(HttpServletResponse response,  String mobile, String pwd);



    JSONArray getUserCorps(String userId);



    JSONObject userAndCorpsByToken(String token);



    JSONObject getMemberInfo(HttpServletResponse response, String userId,String corpId);



    JSONObject getMemberInfo(String memberId);



    Boolean logout(HttpServletResponse response,String userToken,String memberToken);



    JSONArray listAppsByCorpId(String corpId);



    String getAppAuthUrl(String url,String redisUrl);


    String getContactsId(String corpId);



    JSONArray getContactsList(String corpId,String userId,String groupId);


    /**
     * 群组查询
     * @param corpId
     * @param userId
     * @param contactsId
     * @return
     */
    JSONArray getContactsGroupList(String corpId,String userId,String contactsId);


    /**
     * 成员查询
     * @param corpId
     * @param userId
     * @param contactsId
     * @return
     */
    JSONArray getContactsMemberList(String corpId,String userId,String contactsId);




    JSONObject getMsgByGroupId(String groupId);

//    JSONObject getMemberInfo(String memberId,String spId,String userId,String contactsId);

}
