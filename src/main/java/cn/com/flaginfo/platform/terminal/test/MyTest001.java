package cn.com.flaginfo.platform.terminal.test;

import com.alibaba.fastjson.JSON;
import com.mysql.cj.xdevapi.JsonArray;

import java.util.ArrayList;
import java.util.List;

public class MyTest001 {

    public static void main(String[] args){
        String abc="AcbDD";
        List<String> tt=new ArrayList<>(3);
        tt.add(abc);
        System.out.println(JSON.toJSONString(tt));

        for(int i=0;i<tt.size();i++){
            tt.set(i,"aaaaaaa");
        }
        System.out.println(JSON.toJSONString(tt));
        getLo(tt);

        System.out.println(JSON.toJSONString(tt));
    }

    public static void getLo(List<String> abc){
        abc.add("ffff");
        for(int i=0;i<abc.size();i++){
            abc.set(i,"ttttt");
        }
    }

}
