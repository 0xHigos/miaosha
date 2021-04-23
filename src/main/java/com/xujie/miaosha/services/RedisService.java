package com.xujie.miaosha.services;

import com.alibaba.fastjson.JSON;
import com.xujie.miaosha.redis.KeyPrefix;
import com.xujie.miaosha.redis.UserKey;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@Service
public class RedisService {

    @Resource
    JedisPool pool;


    /*
     * 获取单个对象
     * */
    public <T> T get( KeyPrefix prefix, String key, Class<T> clazz ) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            T t = StringToBean(str, clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    /*
     * 设置单个对象
     * */
    public <T> boolean set( KeyPrefix prefix, String key, T value ) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            String val = beanToString(value);
            if (val == null || val.length() == 0) {
                return false;
            }
            String realKey = prefix.getPrefix() + key;
            int seconds = prefix.expireSeconds();
            if (seconds <= 0) {
                jedis.set(realKey, val);
            } else {
                jedis.setex(realKey, seconds, val);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    /*
     * 判断时候存在
     * */
    public <T> boolean exists( KeyPrefix prefix, String key, Class<T> clazz ) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /*
     * 增加值
     * */
    public <T> Long incr( KeyPrefix prefix, String key ) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /*
     * 减少值
     * */
    public <T> Long decr( KeyPrefix prefix, String key ) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }


    /*
     * 删除缓存
     * */
    public boolean delete( KeyPrefix prefix, String key ) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            Long del = jedis.del(key);
            return del > 0;
        } finally {
            returnToPool(jedis);
        }
    }

    private <T> String beanToString( T value ) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    private <T> T StringToBean( String str, Class<T> clazz ) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        } else if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    private void returnToPool( Jedis jedis ) {
        if (jedis != null) {
            jedis.close();
        }
    }


}
