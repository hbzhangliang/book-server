package cn.com.flaginfo.platform.terminal.controller;

import cn.com.flaginfo.platform.api.common.base.BaseResponse;
import cn.com.flaginfo.platform.terminal.services.AuthApi;
import cn.com.flaginfo.platform.terminal.services.TryApi;
import cn.com.flaginfo.platform.terminal.services.UserApi;
import cn.com.flaginfo.platform.terminal.utils.CodeUtils;
import cn.com.flaginfo.platform.terminal.utils.CookieUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Validated
@Controller
@RequestMapping(value = "/",produces = "application/json; charset=utf-8")
public class AuthController {

    @RequestMapping(value = "auth/check")
    @ResponseBody
    public Object check(@RequestBody Map<String,String> map, HttpServletRequest request, HttpServletResponse response){
        String redisUser="User_Token";
        String redisMember="Member_Token";

        String encryptedUserToken = CookieUtils.getCookie(request, redisUser);
        String encryptedMemberToken=CookieUtils.getCookie(request, redisMember);

        //如果UserToken 是空，走登陆
        if(StringUtils.isBlank(encryptedUserToken)){
            String mobile=map.get("account");
            String pwd=map.get("password");
            JSONObject user=authApi.check(response, mobile,pwd);
            JSONArray corps=authApi.getUserCorps(user.getString("userId"));

            JSONObject p=new JSONObject(5);
            p.put("user",user);
            p.put("corps",corps);
            return new BaseResponse<>(null,p);
        }
        //如果成员
        else{
            //跳转到 选择企业
            if(StringUtils.isBlank(encryptedMemberToken)){
                String userToken= CodeUtils.getDecodedToken(encryptedUserToken);
                return new BaseResponse<>(null,authApi.userAndCorpsByToken(userToken));
            }
            //跳转到 进入企业后 成员信息
            else {
                String memberToken=CodeUtils.getDecodedToken(encryptedMemberToken);
                return new BaseResponse<>(null,authApi.getMemberInfo(memberToken));
            }
        }
    }


    @RequestMapping(value = "corp/list")
    @ResponseBody
    public Object corpList(HttpServletRequest request,HttpServletResponse response){
        String redisUser="User_Token";
        String redisMember="Member_Token";

        String encryptedUserToken = CookieUtils.getCookie(request, redisUser);
        String encryptedMemberToken=CookieUtils.getCookie(request, redisMember);
        String userToken= CodeUtils.getDecodedToken(encryptedUserToken);
        return new BaseResponse<>(null,authApi.userAndCorpsByToken(userToken));
    }



    @RequestMapping(value = "corp/enter")
    @ResponseBody
    public Object corpEnter(@RequestBody Map<String,String> map,HttpServletResponse response){
        String userId=map.get("userId"),corpId=map.get("corpId");
        return new BaseResponse<>(null,authApi.getMemberInfo(response,userId,corpId));
    }




    @RequestMapping(value = "member/info")
    @ResponseBody
    public Object memberInfo(HttpServletRequest request){
        String redisUser="User_Token";
        String redisMember="Member_Token";
        String encryptedUserToken = CookieUtils.getCookie(request, redisUser);
        String encryptedMemberToken=CookieUtils.getCookie(request, redisMember);
        String memberToken=CodeUtils.getDecodedToken(encryptedMemberToken);
        return new BaseResponse<>(null,authApi.getMemberInfo(memberToken));
    }




    @RequestMapping(value = "auth/logout")
    @ResponseBody
    public Object logout(HttpServletRequest request,HttpServletResponse response){
        String redisUser="User_Token";
        String redisMember="Member_Token";
        String encryptedUserToken = CookieUtils.getCookie(request, redisUser);
        String encryptedMemberToken=CookieUtils.getCookie(request, redisMember);
        String userToken= CodeUtils.getDecodedToken(encryptedUserToken);

        String memberToken=CodeUtils.getDecodedToken(encryptedMemberToken);
        return new BaseResponse<>(null,authApi.logout(response,userToken,memberToken));
    }


    @RequestMapping(value = "user/change-pwd")
    @ResponseBody
    public Object changePwd(@RequestBody Map<String,String> map,HttpServletRequest request){
        String redisUser="User_Token";
        String encryptedUserToken = CookieUtils.getCookie(request, redisUser);
        String userToken= CodeUtils.getDecodedToken(encryptedUserToken);

        JSONObject jsonObject=authApi.userAndCorpsByToken(userToken);
        String userId=jsonObject.getJSONObject("user").getString("userId");

        String password=map.get("password");
        String newPassword=map.get("newPassword");
        return new BaseResponse<>(null,userApi.changePwd(userId,password,newPassword));
    }


    @RequestMapping(value = "corp/applist")
    @ResponseBody
    public Object appList(HttpServletRequest request){
        String redisUser="User_Token";
        String redisMember="Member_Token";
        String encryptedUserToken = CookieUtils.getCookie(request, redisUser);
        String encryptedMemberToken=CookieUtils.getCookie(request, redisMember);
        String memberToken=CodeUtils.getDecodedToken(encryptedMemberToken);
        String strCorpId= authApi.getMemberInfo(memberToken).getString("strCorpId");
        return  new BaseResponse<>(null,authApi.listAppsByCorpId(strCorpId));
    }



    @RequestMapping(value = "app/appurl")
    @ResponseBody
    public Object appUrl(HttpServletRequest request,@RequestBody Map<String,String> map){
        String yxtUrl=map.get("url");
        String redisUser="User_Token";
        String redisMember="Member_Token";
        String encryptedUserToken = CookieUtils.getCookie(request, redisUser);
        String encryptedMemberToken=CookieUtils.getCookie(request, redisMember);
        String memberToken=CodeUtils.getDecodedToken(encryptedMemberToken);
        return new BaseResponse<>(null,authApi.getAppAuthUrl(yxtUrl,memberToken));
    }





    @RequestMapping(value = "contacts/list")
    @ResponseBody
    public Object contactsList(HttpServletRequest request,@RequestBody Map<String,String> map){
        String redisMember="Member_Token";
        String encryptedMemberToken=CookieUtils.getCookie(request, redisMember);
        String memberToken=CodeUtils.getDecodedToken(encryptedMemberToken);
        JSONObject memberInfo=authApi.getMemberInfo(memberToken);

        String userId=memberInfo.getString("strUserId");
        String spId=memberInfo.getString("strCorpId");
        String groupId=map.get("groupId");
        return new BaseResponse<>(null,authApi.getContactsList(spId,userId,groupId));
    }








    //聊天相关的功能提供
    /**
     * 群组列表查询
     * @param request
     * @return
     */
    @RequestMapping(value = "group/list")
    @ResponseBody
    public Object groupList(HttpServletRequest request){
        String redisMember="Member_Token";
        String encryptedMemberToken=CookieUtils.getCookie(request, redisMember);
        String memberToken=CodeUtils.getDecodedToken(encryptedMemberToken);
        JSONObject memberInfo=authApi.getMemberInfo(memberToken);

        String mobile=memberInfo.getString("mdn");
        return new BaseResponse<>(null,tryApi.getGroupList(mobile));
    }

    /**
     * 群组列表的更新数据
     * @param request
     * @return
     */
    @RequestMapping(value = "group/list/new")
    @ResponseBody
    public Object groupListNews(HttpServletRequest request){
        String redisMember="Member_Token";
        String encryptedMemberToken=CookieUtils.getCookie(request, redisMember);
        String memberToken=CodeUtils.getDecodedToken(encryptedMemberToken);
        JSONObject memberInfo=authApi.getMemberInfo(memberToken);

        String mobile=memberInfo.getString("mdn");
        return new BaseResponse<>(null,tryApi.getGroupNews(mobile));
    }


    /**
     * 创建群聊
     * @param request
     * @return
     */
    @RequestMapping(value = "group/create")
    @ResponseBody
    public Object groupCreate(HttpServletRequest request,@RequestBody Map<String,Object> map){
        String groupName=(String) map.get("groupName");
        List<Map<String,String>> contacts =(List<Map<String,String>> )map.get("contacts");

        String redisMember="Member_Token";
        String encryptedMemberToken=CookieUtils.getCookie(request, redisMember);
        String memberToken=CodeUtils.getDecodedToken(encryptedMemberToken);
        JSONObject memberInfo=authApi.getMemberInfo(memberToken);

        String mobile=memberInfo.getString("mdn");

        return new BaseResponse<>(null,tryApi.createGroup(mobile,groupName,contacts));
    }


    /**
     * 退出群聊
     * @param request
     * @return
     */
    @RequestMapping(value = "group/quit")
    @ResponseBody
    public Object groupQuit(HttpServletRequest request,@RequestBody Map<String,String> map){
        String groupId=map.get("groupId");

        String redisMember="Member_Token";
        String encryptedMemberToken=CookieUtils.getCookie(request, redisMember);
        String memberToken=CodeUtils.getDecodedToken(encryptedMemberToken);
        JSONObject memberInfo=authApi.getMemberInfo(memberToken);

        String mobile=memberInfo.getString("mdn");

        return new BaseResponse<>(null,tryApi.quitGroup(mobile,groupId));
    }


    @RequestMapping(value = "group/msg/get")
    @ResponseBody
    public Object groupMsgGet(HttpServletRequest request,@RequestBody Map<String,String> map){
        String groupId=map.get("groupId");

        String redisMember="Member_Token";
        String encryptedMemberToken=CookieUtils.getCookie(request, redisMember);
        String memberToken=CodeUtils.getDecodedToken(encryptedMemberToken);
        JSONObject memberInfo=authApi.getMemberInfo(memberToken);

        String mobile=memberInfo.getString("mdn");

        return new BaseResponse<>(null,tryApi.getMsg(groupId,mobile));
    }


    @RequestMapping(value = "group/msg/send")
    @ResponseBody
    public void groupMsgSend(HttpServletRequest request,@RequestBody Map<String,String> map){
        String groupId=map.get("groupId");
        String content=map.get("content");

        String redisMember="Member_Token";
        String encryptedMemberToken=CookieUtils.getCookie(request, redisMember);
        String memberToken=CodeUtils.getDecodedToken(encryptedMemberToken);
        JSONObject memberInfo=authApi.getMemberInfo(memberToken);

        String mobile=memberInfo.getString("mdn");

        tryApi.sendMsg(groupId,mobile,content);
    }


    @Autowired
    private TryApi tryApi;


    @Autowired
    private AuthApi authApi;

    @Autowired
    private UserApi userApi;

}
