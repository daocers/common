package co.bugu.framework.util;

import co.bugu.framework.util.exception.TesException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by user on 2017/5/17.
 */
public class JedisUtilNew {
    private static Logger logger = LoggerFactory.getLogger(JedisUtil.class);

    private static JedisPool pool;

    static {
        InputStream inputStream = JedisPool.class.getClassLoader().getResourceAsStream("conf/redis.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("[redis配置文件加载失败]", e);
        }

        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(Integer.parseInt(properties.getProperty("redis.maxIdle")));
            config.setMaxTotal(Integer.parseInt(properties.getProperty("redis.maxTotal")));
            config.setMaxWaitMillis(Integer.parseInt(properties.getProperty("redis.maxWaitMillis")));

            //String password = properties.getProperty("redis.password");

            pool = new JedisPool(config, properties.getProperty("redis.host"),
                    Integer.parseInt(properties.getProperty("redis.port", "6379")),
                    Integer.parseInt(properties.getProperty("redis.timeout", "5000")),
                    properties.getProperty("redis.password"),
                    Integer.parseInt(properties.getProperty("redis.db")));
        } catch (Exception e) {
            logger.error("[初始化jedisPool失败]", e);
            pool = null;
        }
    }

    public static void release(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public static void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("[jedis异常] set", e);
        } finally {
            release(jedis);
        }
    }

    /**
     * @param key
     * @param value
     * @Description: 存放set类型
     */
    public static void sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("[jedis异常] set", e);
        } finally {
            release(jedis);
        }
    }


    /**
     * @param key
     * @param value
     * @return boolean
     * @Title: sexists
     * @Description: set中是否存在指定元素
     */
    public static boolean sexists(String key, String value) {
        Jedis jedis = null;
        boolean flag = false;
        try {
            jedis = pool.getResource();
            flag = jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("[jedis异常] set", e);
        } finally {
            release(jedis);
        }
        return flag;
    }

    public static void srem(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("[jedis异常] set", e);
        } finally {
            release(jedis);
        }
    }

    public static Set<String> getAllOfSet(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.smembers(key);
        } catch (Exception e) {
            logger.error("smembers 异常", e);
            return null;
        } finally {
            release(jedis);
        }
    }

    /**
     * 获取set长度
     *
     * @param key
     * @return
     */
    public static Long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("scard 异常", e);
            return null;
        } finally {
            release(jedis);
        }
    }

    public static String get(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("[jedis异常] get", e);
            return null;
        } finally {
            release(jedis);
        }
    }

    public static void putObject(String key, Object obj) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hmset(key, getValueMap(obj));
        } catch (Exception e) {
            logger.error("[jedis异常] putObject", e);
        } finally {
            release(jedis);
        }
    }


    public static Map<String, String> getMap(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hgetAll(key);
        } catch (Exception e) {
            logger.error("[jedis 异常] getObject", e);
        } finally {
            release(jedis);
        }
        return null;
    }

    public static void del(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.del(key);
        } catch (Exception e) {
            logger.error("[jedis 异常] del", e);
        } finally {
            release(jedis);
        }
    }


    public static Long incr(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.incr(key);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
            return 0L;
        } finally {
            release(jedis);
        }
    }

    public static Long incrby(String key, Long num) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.incrBy(key, num);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
            return 0L;
        } finally {
            release(jedis);
        }
    }


    public static Boolean exists(String key) {
        Jedis jedis = null;
        boolean exists = false;
        try {
            jedis = getJedis();
            exists = jedis.exists(key);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            release(jedis);
        }
        return exists;
    }

    public static void decrby(String key, Long num) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.decrBy(key, num);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            release(jedis);
        }
    }

    public static void decr(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.decr(key);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            release(jedis);
        }
    }

    public static void push(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            release(jedis);
        }
    }

    public static void pushList(String key, List<String> value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
//            for (String item : value) {
//                jedis.rpush(item);
//            }
            jedis.rpush(key, value.toArray(new String[value.size()]));
        } catch (Exception e) {
            logger.error("jedis pushList 异常", e);
        } finally {
            release(jedis);
        }
    }


    public static void lrem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.lrem(key, 1, value);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            release(jedis);
        }
    }

    public static List<String> getList(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.lrange(key, 0, -1);
        } catch (Exception e) {
            logger.error("jedis lrange 异常", e);
            return null;
        } finally {
            release(jedis);
        }
    }

    public static Map<String, String> hgetall(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hgetAll(key);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            release(jedis);
        }
        return null;
    }

    public static List<String> hmget(String key, String... filed) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hmget(key, filed);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            release(jedis);
        }
        return null;
    }

    public static void hmset(String key, Map<String, String> maps) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hmset(key, maps);
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            release(jedis);
        }
    }


    public static List<String> lrange(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            List<String> list = jedis.lrange(key, 0, -1);
            return list;
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            release(jedis);
        }
        return null;
    }


    public static Set<String> zrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<String> objs = jedis.zrange(key, start, end);
            return objs;
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            release(jedis);
        }
        return null;
    }

    public static int zadd(String key, Map<String, Double> maps) {
        int res = 0;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.zadd(key, maps);
            res = 1;
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            release(jedis);
        }
        return res;
    }

    public static Set<String> zrevrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<String> objs = jedis.zrevrange(key, start, end);
            return objs;
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            release(jedis);
        }
        return null;
    }

    /**
     * 设置过期时间, 单位为秒
     *
     * @param key
     * @param secondes
     * @return
     */
    public static int expire(String key, int secondes) {
        int res = 0;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.expire(key, secondes);
            res = 1;
        } catch (Exception e) {
            logger.error("jedis 异常", e);
        } finally {
            release(jedis);
        }
        return res;
    }


    /**
     * 获取Jedis实例
     *
     * @return
     */
    public static Jedis getJedis() {
        try {
            if (pool != null) {
                Jedis Jedis = pool.getResource();
                return Jedis;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("jedis 异常", e);
            return null;
        }
    }


    public static Map<String, String> getValueMap(Object obj) {
        Map<String, String> map = new HashMap<String, String>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            try {
                boolean accessFlag = fields[i].isAccessible();
                fields[i].setAccessible(true);
                Object o = fields[i].get(obj);
                if (o != null) {
                    if (o instanceof Date) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        map.put(varName, dateFormat.format((Date) o));
                    } else {
                        map.put(varName, o.toString());
                    }

                }
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                logger.error("[jedis 异常] getValueMap", ex);
            } catch (IllegalAccessException ex) {
                logger.error("[jedis异常] getValueMap", ex);
            }
        }
        return map;
    }


    public static void set(byte[] key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("[jedis调用异常]", e);
        } finally {
            release(jedis);
        }
    }

    /**
     * 将实体类放入到redis上
     *
     * @param key
     * @param object
     */
    public static void setJson(String key, Object object) {
        Jedis jedis = null;
        try {
            String res = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss:sss", SerializerFeature.PrettyFormat);
            jedis = pool.getResource();
            jedis.set(key, res);
        } catch (Exception e) {
            logger.error("jedis setJson异常", e);
        } finally {
            release(jedis);
        }
    }

    /**
     * 获取保存的json对象，适用于没有implement Serializable的对象
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getJson(String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            String res = jedis.get(key);
            return JSON.parseObject(res, clazz);
        } catch (Exception e) {
            logger.error("jedis getJson异常", e);
        } finally {
            release(jedis);
        }
        return null;
    }

    /**
     * 删除指定的key
     *
     * @param key 需要删除的key，可以使用模糊查询等
     */
    public static void delKeysLike(String key) throws TesException {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Set<String> keys = jedis.keys(key);
            String[] tar = keys.toArray(new String[keys.size()]);
            jedis.del(tar);
        } catch (Exception e) {
            logger.error("jedis getKeyLike 异常", e);
            throw new TesException("Jedis操作失败", e);
        } finally {
            release(jedis);
        }
    }

    /**
     * 查找对应的key
     *
     * @param key
     * @return
     * @throws TesException
     */
    public static Set<String> keysLike(String key) throws TesException {
        Jedis jedis = null;
        try {
            if (!key.endsWith("*")) {
                key = key + "*";
            }
            jedis = pool.getResource();
            Set<String> keys = jedis.keys(key);
            return keys;
        } catch (Exception e) {
            logger.error("jedis keysLike 异常", e);
            throw new TesException("jedis操作失败", e);
        } finally {
            release(jedis);
        }
    }


    /**
     * 获取交集的数量
     *
     * @param keys
     * @return
     * @throws TesException
     */
    public static int sinterForSize(String... keys) throws TesException {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Set<String> set = jedis.sinter(keys);
            return set.size();
        } catch (Exception e) {
            logger.error("jedis sinter异常", e);
            throw new TesException("jedis操作失败", e);
        } finally {
            release(jedis);
        }
    }


    /**
     * 获取交集
     *
     * @param keys
     * @return
     * @throws TesException
     */
    public static Set<String> sinterForObj(String... keys) throws TesException {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Set<String> set = jedis.sinter(keys);
            return set;
        } catch (Exception e) {
            logger.error("jedis sinter异常", e);
            throw new TesException("jedis操作失败", e);
        } finally {
            release(jedis);
        }
    }


    public static void hSet(String key, String field, String val) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hset(key, field, val);
        } catch (Exception e) {
            logger.error("hSet 失败", e);
        } finally {
            release(jedis);
        }
    }

    public static void hmSet(String key, Map<String, String> map) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hmset(key, map);
        } catch (Exception e) {
            logger.error("hmset 失败", e);
        } finally {
            release(jedis);
        }
    }

    public static String hGet(String key, String field) {
        String res = "";
        Jedis jedis = null;
        try {
            jedis = getJedis();
            res = jedis.hget(key, field);
        } catch (Exception e) {
            logger.error("hGet 失败", e);
        } finally {
            release(jedis);
        }
        return res;
    }

    public static List<String> hmGet(String key, String... field) {
        List<String> res = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            res = jedis.hmget(key, field);
        } catch (Exception e) {
            logger.error("hmGet 失败", e);
        } finally {
            release(jedis);
        }
        return res;
    }

    public static Map<String, String> hGetAll(String key) {
        Map<String, String> map = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            map = jedis.hgetAll(key);
        } catch (Exception e) {
            logger.error("hGetAll 失败", e);
        } finally {
            release(jedis);
        }
        return map;

    }

}
