package cn.com.flaginfo.platform.terminal.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private static final PropertiesUtils redisProper = new PropertiesUtils("spring/redis-time-config.properties");


    /**
     * 删除keys值
     * @param keys
     */
    public void delKeys(String ... keys){
        if(keys!=null&&keys.length>0){
            if(keys.length==1){
                redisTemplate.delete(keys[0]);
            }
            else {
                redisTemplate.delete(CollectionUtils.arrayToList(keys));
            }

        }
    }


    /**
     * 删除缓存， 匹配删除，效率低
     * @param patners
     */
    public void delKeysPatners(String... patners){
        if(patners!=null&&patners.length>0){
            for(String item:patners){
                redisTemplate.delete(redisTemplate.keys(item+"*"));
            }
        }
    }



    public List<Object> getObjPatners(String patners){
        String pt=String.format("0%s*",patners);

        Set<String> keys=redisTemplate.keys(pt);
        List<Object> result=new ArrayList<>(50);
        for(String item:keys){
            result.add(this.getObj(item));
        }
        return result;
    }



    /**
     * 查询key值
     * @param key
     * @return
     */
    public Object getObj(String key){
        return redisTemplate.boundValueOps(key).get();
    }


    /**
     * 获取key值  and delete it by left
     * @param key
     * @param left
     * @return
     */
    public Object getObj(String key,Boolean left){
        Object object=redisTemplate.boundValueOps(key).get();
        if(!left){
            redisTemplate.delete(key);
        }
        return object;
    }


    /**
     * 添加缓存
     * @param key
     * @param value
     * @param redisKey
     */
    public void setObj(String key, Object value,String redisKey){
        //添加缓存
        String[] redispropers=redisProper.getPropertiesValues(redisKey);
        Long redisExp=Long.valueOf(redispropers[0]);
        TimeUnit redisTimeType= UtilHelper.getTimeUtil(redispropers[1]);
        redisTemplate.opsForValue().set(key,value,redisExp, redisTimeType);
    }




    // 对list 对相关操作
    public void addLList(String key,Object value){
        Long l=redisTemplate.opsForList().size(key);
        if(l==null||Long.compare(l,0L)==0){
            redisTemplate.opsForList().set(key,0,value);
        }
        else {
            redisTemplate.opsForList().leftPush(key, value);
        }
    }

    public void addRList(String key,Object value){
        Long l=redisTemplate.opsForList().size(key);
        if(l==null||Long.compare(l,0L)==0){
            redisTemplate.opsForList().set(key,0,value);
        }
        else {
            redisTemplate.opsForList().rightPush(key, value);
        }
    }

    public void setList(String key,List<Object> value){
        int index=0;
        for(Object item:value){
            redisTemplate.opsForList().set(key,index++,item);
        }
    }

    public List<Object> getList(String key){
        return redisTemplate.opsForList().range(key,0,redisTemplate.opsForList().size(key));
    }



    //set op
    public void setSet(String key,List<Object> value){
        redisTemplate.opsForSet().add(key,value.toArray());
    }

    public Set<Object> getSet(String key){
        return redisTemplate.opsForSet().members(key);
    }



    //hash op

    public void setHash(String key, Map map){
        redisTemplate.opsForHash().putAll(key,map);
    }


    public Map getHash(String key){
        return  redisTemplate.opsForHash().entries(key);
    }



}
