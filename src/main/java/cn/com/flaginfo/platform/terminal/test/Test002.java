package cn.com.flaginfo.platform.terminal.test;

import cn.com.flaginfo.platform.terminal.utils.HtmlUtils;
import cn.com.flaginfo.platform.terminal.utils.JSONHelper;

public class Test002 {

    public static void main(String[] args){

        try{
            String strUrl = "http://www.kanunu8.com/book/4573/62828.html";

            String content = JSONHelper.loadJson(strUrl);

            System.out.println(content);

            String ss= HtmlUtils.getSubContent(content);

            System.out.println(ss);

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
