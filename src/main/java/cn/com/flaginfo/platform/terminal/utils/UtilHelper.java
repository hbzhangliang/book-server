package cn.com.flaginfo.platform.terminal.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UtilHelper {


    /**
     * 连接字符串  z作为redis的key值
     * @param args
     * @return
     */
    public static String contacsString(Object ...args){
        if(args!=null&&args.length>0) {
            int index=0;
            StringBuilder sb=new StringBuilder();
            for (Object p : args) {
                if (null != p&&StringUtils.isNotBlank(String.valueOf(p))) {
                    sb.append(index+String.valueOf(p));
                }
                else {
                    sb.append(index+"_");
                }
                index++;
            }
            return sb.toString();
        }
        return null;
    }


    public static TimeUnit getTimeUtil(String type){
        if(StringUtils.isEmpty(type)) return TimeUnit.SECONDS;
        TimeUnit result=null;
        switch (type){
            case "S":result =TimeUnit.SECONDS;break;
            case "M":result =TimeUnit.MINUTES;break;
            case "H":result=TimeUnit.HOURS;break;
            case "D":result=TimeUnit.DAYS;break;
            default:result=TimeUnit.SECONDS;
        }
        return result;
    }




    //获取当前时间的String值
    public static String getNowStrTime(){
        return sdf.format(new Date());
    }

    private  static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

}
