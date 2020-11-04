package cn.agtsci.commons.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis工具类，为key加前缀
 *
 * @author caiyt
 * @date 2019-11-05
 */
@Component
public class RedisUtils {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    /**
     *  redis里存入数据,不过期
     *
     * @param key
     * @param value
     */
    public void setRedis(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * redis里存入数据和设置缓存时间
     *
     * @param key
     * @param value
     * @param time
     * @param unit
     */
    public void setRedisAndexpire(Object key, Object value, Long time, TimeUnit unit) {
        redisTemplate.opsForValue().set(key,value,time,unit);
    }

    /**
     * 获取redis里的数据
     *
     * @param key
     */
    public Object getRedis(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除redis里的数据
     *
     *
     * @param key
     * ***/
    public boolean delRedis(Object key){
        return redisTemplate.delete(key);
    }

}
