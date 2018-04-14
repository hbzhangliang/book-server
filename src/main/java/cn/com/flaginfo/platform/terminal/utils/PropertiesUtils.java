package cn.com.flaginfo.platform.terminal.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Properties;

/**
 *
 *
 */
public class PropertiesUtils {

  private String Properties_File_Path; //默认路径

  public PropertiesUtils(String path){
    this.Properties_File_Path = path;
  }

  /**
   * 此方法只支持读取src目录property文件
   *
   * @param key 要读取的配置键
   * @return String 配置值
   */
  public String getPropertiesValue(String key) {
    Properties props = new Properties();
    try {
      props.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(Properties_File_Path));
      return props.getProperty(key);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 以逗号分割
   * @param key
   * @return
   */
  public String[] getPropertiesValues(String key){
    Properties props = new Properties();
    try {
      props.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(Properties_File_Path));
      String temp= props.getProperty(key);
      if(StringUtils.isEmpty(temp)){
        return null;
      }
      return  temp.split(",");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  /**
   * 拼接内容，参数为内容KEY+参数数组
   *
   * @param key  配置文件中的key
   * @param strs 占位符替换内容的数组
   * @return String
   */
  public String getValueWithPH(String key, String... strs) {
    return MessageFormat.format(getPropertiesValue(key), strs);
  }
}
