package cn.com.flaginfo.platform.terminal.test;

import cn.com.flaginfo.platform.terminal.utils.HtmlUtils;
import cn.com.flaginfo.platform.terminal.utils.JSONHelper;
import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

public class Test003 {


    public static void main(String[] args){
        try {
            ////1-----9
            ////
            String strUrl = "http://www.kanunu8.com/author1.html";
            String content = JSONHelper.loadJson(strUrl);

            System.out.println(content);
            List<Map<String,String>> list= HtmlUtils.getAuthList(content);

            System.out.println(JSON.toJSONString(list));
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

}
