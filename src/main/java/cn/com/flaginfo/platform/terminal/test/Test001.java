package cn.com.flaginfo.platform.terminal.test;

import cn.com.flaginfo.platform.terminal.utils.HtmlUtils;
import cn.com.flaginfo.platform.terminal.utils.JSONHelper;
import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

public class Test001 {


    public static void main(String[] args){
        try {
            String strUrl = "http://www.kanunu8.com/book/4573";

            String content = JSONHelper.loadJson(strUrl);

            System.out.println(content);


            String tableContent= HtmlUtils.getTableContent(content);

            System.out.println(JSON.toJSONString(tableContent));


            List<String> tableTds=HtmlUtils.getTableTdList(tableContent);

            System.out.println(JSON.toJSONString(tableTds));

            List<Map<String,String>> mapList=HtmlUtils.getTdinfoaList(tableTds);

            System.out.println(JSON.toJSONString(mapList));


        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

}
