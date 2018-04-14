package cn.com.flaginfo.platform.terminal.test;

import cn.com.flaginfo.platform.terminal.utils.HtmlUtils;
import cn.com.flaginfo.platform.terminal.utils.JSONHelper;
import cn.com.flaginfo.platform.terminal.utils.UtilHelper;
import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

/**
 * Created by liang_zhang on 2017/10/14.
 */
public class Test {


    public static void main(String[] args){

        try {
            String strUrl = "http://www.kanunu8.com/zj/10867.html";
            String content = JSONHelper.loadJson(strUrl);

            content= HtmlUtils.delBlank(content);

            System.out.println(content);

            String tableContent=HtmlUtils.getTableContent(content);

            System.out.println(tableContent);

            List<String> tbList=HtmlUtils.getTableTdList(tableContent);

            System.out.println(JSON.toJSONString(tbList));

            List<Map<String,String>> mapList=HtmlUtils.getTdinfoaList(tbList);

            System.out.println(JSON.toJSONString(mapList));

        }
        catch ( Exception e){
            e.printStackTrace();

        }


    }


}
