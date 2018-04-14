package cn.com.flaginfo.platform.terminal.test;

import cn.com.flaginfo.platform.terminal.utils.JSONHelper;
import cn.com.flaginfo.platform.terminal.utils.UtilHelper;

/**
 * Created by liang_zhang on 2017/10/14.
 */
public class Test {


    public static void main(String[] args){

        try {
            String strUrl = "https://www.cnblogs.com/mafeng/p/5651323.html";
            String content = JSONHelper.loadJson(strUrl);

            System.out.println(content);
        }
        catch ( Exception e){
            e.printStackTrace();

        }


    }


}
