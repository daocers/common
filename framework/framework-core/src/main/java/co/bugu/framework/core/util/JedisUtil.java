package co.bugu.framework.core.util;

import co.bugu.framework.core.exception.TesJedisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by user on 2017/5/17.
 */
public class JedisUtil {
    private static Logger logger = LoggerFactory.getLogger(JedisUtil.class);

    public static final Integer MONTH_SECONDS = 60 * 60 * 24 * 30;
    public static final Integer WEEK_SECONDS = 60 * 60 * 24 * 7;
    public static final Integer DAY_SECONDS = 60 * 60 * 24;
    public static final Integer HOUR_SECONDS = 60 * 60;

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

            String nodeInfo = properties.getProperty("redis.clusterNodes");
            String[] nodes = nodeInfo.split(";");
            if (nodes.length > 0) {
                String[] item = nodes[0].split(":");
                String host = item[0];
                Integer port = Integer.parseInt(item[1]);
                pool = new JedisPool(config, host, port,
                        Integer.parseInt(properties.getProperty("redis.timeout", "5000")),
                        properties.getProperty("redis.password"),
                        Integer.parseInt(properties.getProperty("redis.db")));
            } else {
                throw new TesJedisException("redis.clusterNodes配置异常，请检查！");
            }
        } catch (TesJedisException e) {
            logger.error("初始化异常", e);
        }
    }

    /**
     * 释放资源
     *
     * @param jedis
     */
    public static void release(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public static Jedis getJedis() {
        if (pool != null) {
            Jedis Jedis = pool.getResource();
            return Jedis;
        } else {
            return null;
        }
    }

    /**
     * 删除指定的key
     * @param key
     * @return
     */
    public static Long delKey(String key){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            return jedis.del(key);
        }finally {
            jedis.close();
        }
    }


    /**
     * 根据类创建缓存的 key
     * 适用于主见为id的实体类
     *
     * @param obj
     * @return
     */
    public static String getKey(Object obj) throws TesJedisException {
        try {
            String key = obj.getClass().getName() + "_" + ReflectUtil.get(obj, "id");
            return key;
        } catch (Exception e) {
            logger.error("获取缓存key失败", e);
            throw new TesJedisException("获取缓存key失败", e);
        }
    }

    public static String getKey(Integer id, Object obj){
        String key = obj.getClass().getName() + "_" + id;
        return key;
    }

    public static String getKey(Integer id, Class<?> clazz){
        String key = clazz.getName() + "_" + id;
        return key;
    }

    /**
     * 获取缓存过期的时间
     * 默认为一个月
     *
     * @param seconds
     * @return
     */
    private static Integer getExpireSeconds(Integer... seconds) {
        Integer expireSeconds = 0;
        if (seconds.length == 0) {
            expireSeconds = MONTH_SECONDS;
        } else {
            expireSeconds = seconds[0];
        }
        return expireSeconds;
    }
/**===========================================*/
    /**
     * key 操作
     */


    /**
     * 将实体类放入到redis上
     * protostuff进行序列化
     *
     * @param key     缓存的key，通常情况下，建议key自动生成，
     * @param object  缓存的实体对象
     * @param seconds 超时时间
     */
    public static void setObject(String key, Object object, Integer... seconds) throws IOException {
        Jedis jedis = null;


        try {
            jedis = getJedis();
            byte[] bytes = SerializationUtil.serialize(object);
            jedis.setex(key.getBytes("utf-8"), getExpireSeconds(seconds), bytes);
        } finally {
            release(jedis);
        }
    }


    /**
     * 把实体类序列化后直接放入到缓存
     * 采用protostuff序列化
     *
     * @param object
     * @param seconds
     * @throws IOException
     * @throws TesJedisException
     */
    public static void setObject(Object object, Integer... seconds) throws IOException, TesJedisException {
        Jedis jedis = null;
        try {
            byte[] bytes = SerializationUtil.serialize(object);
            jedis = getJedis();
            jedis.setex(getKey(object).getBytes("utf-8"), getExpireSeconds(seconds), bytes);
        } finally {
            release(jedis);
        }
    }


    /**
     * 获取保存的序列化对象，
     * 适用于使用setObject保存的对象
     * 通常情况下，key建议自动生成
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getObject(String key, Class<T> clazz) throws Exception {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] res = jedis.get(key.getBytes("utf-8"));
            return SerializationUtil.deserialize(res, clazz);
        } finally {
            release(jedis);
        }
    }

    /**
     * 获取保存的protoStuff对象，适用于没有implement Serializable的对象
     *
     * @param id    实体类的id
     * @param clazz 实体类的类型
     * @param <T>
     * @return
     */
    public static <T> T getObject(Integer id, Class<T> clazz) throws Exception {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String key = clazz.getName() + "_" + id;
            byte[] res = jedis.get(key.getBytes("utf-8"));
            return SerializationUtil.deserialize(res, clazz);
        } finally {
            release(jedis);
        }
    }

    public static void delObject(Object record) throws TesJedisException {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String key = getKey(record);
            jedis.del(key);
        } finally {
            release(jedis);
        }
    }
}
