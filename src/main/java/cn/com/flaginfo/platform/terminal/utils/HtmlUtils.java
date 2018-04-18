package cn.com.flaginfo.platform.terminal.utils;

import cn.com.flaginfo.platform.terminal.controller.TestController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlUtils {

    private static final Logger log = LoggerFactory.getLogger(HtmlUtils.class);

    private String host="http://www.kanunu8.com";

    //作者文章列表

    /**
     * 删除空格
     * @param content
     * @return
     */
    public static String delBlank(String content){
        return content.replace(" ","");
    }

    /**
     * 获取table中的内容
     * @param content
     * @return
     */
    public static String getTableContent(String content){
        if(StringUtils.isEmpty(content)) return null;
        int startPoint=content.indexOf("<table"),endPoint=content.indexOf("</table>")+8;
        return content.substring(startPoint,endPoint);
    }

    /**
     * 获取各个td的内容
     * 首先是全部变成小写
     * @param tableContent
     * @return
     */
    public static List<String> getTableTdList(String tableContent){
        tableContent=tableContent.toLowerCase();
        if(StringUtils.isEmpty(tableContent)) return null;
        List<String> list=new ArrayList<>(200);

        int startPosition=tableContent.indexOf("<td"),endPosition=tableContent.indexOf("</td>")+5;
        while (startPosition!=-1&&endPosition!=-1&&startPosition<endPosition){
            String tmp=tableContent.substring(startPosition,endPosition);
            list.add(tmp.replace("《","").replace("》",""));
            tableContent=tableContent.substring(endPosition);
            startPosition=tableContent.indexOf("<td");
            endPosition=tableContent.indexOf("</td>")+5;
        }
        return list;
    }


    /**
     * 获取td中的a标签内容信息
     * 包含url   name
     * @param tds
     * @return
     */
    public static List<Map<String,String>> getTdinfoaList(List<String> tds){
        if(tds==null||tds.isEmpty()){
            return null;
        }
        List<Map<String,String>> result=new ArrayList<>(200);
        int startPosition=0,endPosition=0;
        String url="",name="";
//        tds.remove(0);//删除第一行的记录，是头部文件，作者简介
        for(String item:tds){
            startPosition=item.indexOf("<a");
            if(startPosition==-1) continue;
            endPosition=item.indexOf("</a>")+4;
            String tagAContent=item.substring(startPosition,endPosition);

            startPosition=tagAContent.indexOf("href=")+6;
            endPosition=tagAContent.indexOf("\"",startPosition);
            url=tagAContent.substring(startPosition,endPosition);

            startPosition=tagAContent.indexOf(">")+1;
            endPosition=tagAContent.indexOf("</a>");
            name=tagAContent.substring(startPosition,endPosition);

            if(name.indexOf("<")!=-1||name.indexOf(">")!=-1){
                continue;//文章 题干列表中 首行需清除
            }

            Map<String,String> tmp=new HashMap<>(2);
            tmp.put("name",name);
            tmp.put("url",url);
            result.add(tmp);
        }

        return result;

    }





    //文章列表
    public static String getSubContent(String htmlContent){
        if(StringUtils.isBlank(htmlContent)) return null;

        int startPosition=htmlContent.indexOf(">p>")+3,endPosition=htmlContent.indexOf("</p>");

        if(startPosition!=-1&&endPosition!=-1&&startPosition<endPosition)
            return htmlContent.substring(startPosition,endPosition).replace("<br />br />","<br/>");
        return null;
    }





    //name url
    //读取作者列表的
    public static List<Map<String,String>>  getAuthList(String content){
        String p=">p><a";
        String q="</a></p>";
//        String startPs=content.indexOf(p);

        content=content.toLowerCase();
        if(StringUtils.isEmpty(content)) return null;
        List<Map<String,String>> list=new ArrayList<>(200);

        int startPosition=content.indexOf(p)+3,endPosition=content.indexOf(q)+4;
        while (startPosition!=-1&&endPosition!=-1&&startPosition<endPosition) {
            String tmp = content.substring(startPosition, endPosition);
            log.info(tmp);
            int pp=tmp.indexOf("\"")+1;
            int qq=tmp.indexOf("\"",pp);
            if(pp==0||qq==-1||pp>qq) break;
            Map<String,String> map=new HashMap<>(2);
            String url=tmp.substring(pp,qq);
            if(url.indexOf("html")==-1){
                break;
            }
            map.put("url",url);
            pp=tmp.indexOf(">")+1;
            qq=tmp.indexOf("<",pp);
            map.put("name",tmp.substring(pp,qq));

            content = content.substring(endPosition);
            startPosition = content.indexOf(p) + 3;
            endPosition = content.indexOf(q) + 4;

            list.add(map);
        }
        return list;
    }



    //when i=3时
    public static List<Map<String,String>>  getAuthListADD(String content){
        String p="<p><a";
        String q="</a></p>";
//        String startPs=content.indexOf(p);

        content=content.toLowerCase();
        if(StringUtils.isEmpty(content)) return null;
        List<Map<String,String>> list=new ArrayList<>(200);

        int startPosition=content.indexOf(p)+3,endPosition=content.indexOf(q)+4;
        while (startPosition!=-1&&endPosition!=-1&&startPosition<endPosition) {
            String tmp = content.substring(startPosition, endPosition);
            log.info(tmp);
            int pp=tmp.indexOf("\"")+1;
            int qq=tmp.indexOf("\"",pp);
            if(pp==0||qq==-1||pp>qq) break;
            Map<String,String> map=new HashMap<>(2);
            String url=tmp.substring(pp,qq);
            if(url.indexOf("html")==-1){
                break;
            }
            map.put("url",url);
            pp=tmp.indexOf(">")+1;
            qq=tmp.indexOf("<",pp);
            map.put("name",tmp.substring(pp,qq));

            content = content.substring(endPosition);
            startPosition = content.indexOf(p) + 3;
            endPosition = content.indexOf(q) + 4;

            list.add(map);
        }
        return list;
    }



}
