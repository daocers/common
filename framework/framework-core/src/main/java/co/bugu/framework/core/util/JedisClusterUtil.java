package co.bugu.framework.core.util;

import co.bugu.framework.core.exception.TesJedisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Created by user on 2017/08/02.
 * redis集群下使用
 */
public class JedisClusterUtil {
    private static Logger logger = LoggerFactory.getLogger(JedisClusterUtil.class);

    public static final Integer MONTH_SECONDS = 60 * 60 * 24 * 30;
    public static final Integer WEEK_SECONDS = 60 * 60 * 24 * 7;
    public static final Integer DAY_SECONDS = 60 * 60 * 24;
    public static final Integer HOUR_SECONDS = 60 * 60;

    public static void main(String[] args) {
        JedisCluster cluster = JedisClusterUtil.getJedisCluster();
        cluster.set("name", "allen");
        JedisClusterUtil.release(cluster);
    }

    private static Set<HostAndPort> jedisClusterNodes;
    private static JedisPoolConfig config;


    static {
        InputStream inputStream = JedisPool.class.getClassLoader().getResourceAsStream("conf/redis.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("[redis配置文件加载失败]", e);
        }

        try {
            config = new JedisPoolConfig();
            config.setMaxIdle(Integer.parseInt(properties.getProperty("redis.maxIdle")));
            config.setMaxTotal(Integer.parseInt(properties.getProperty("redis.maxTotal")));
            config.setMaxWaitMillis(Integer.parseInt(properties.getProperty("redis.maxWaitMillis")));
            config.setTestOnBorrow(true);

//            redis集群的节点集合
            jedisClusterNodes = new HashSet<>();
            String nodeInfo = properties.getProperty("redis.clusterNodes");
            String[] nodeList = nodeInfo.trim().split(",");

            if (nodeList.length > 0) {
                for (String node : nodeList) {
                    String[] item = node.split(":");
                    String ip = item[0];
                    Integer port = Integer.parseInt(item[1]);
                    jedisClusterNodes.add(new HostAndPort(ip, port));
                }
            } else {
                throw new TesJedisException("初始化jedis异常, redis.clusterNodes参数配置有误，需要配置集群");
            }
        } catch (TesJedisException e) {
            logger.error("初始化异常", e);
        }
    }

    /**
     * 释放资源
     *
     * @param jedisCluster
     */
    public static void release(JedisCluster jedisCluster) {
        if (jedisCluster != null) {
            try {
                jedisCluster.close();
            } catch (IOException e) {
                logger.error("redis cluster 释放失败", e);
            }
        }
    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public static JedisCluster getJedisCluster() {
        /**
         * 节点，超时时间、最多重定向次数，连接池
         * */
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, 2000, 100, config);
        return jedisCluster;
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
        JedisCluster jedisCluster = null;


        try {
            jedisCluster = getJedisCluster();
            byte[] bytes = SerializationUtil.serialize(object);
            jedisCluster.setex(key.getBytes("utf-8"), getExpireSeconds(seconds), bytes);
        } finally {
            release(jedisCluster);
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
        JedisCluster jedisCluster = null;
        try {
            byte[] bytes = SerializationUtil.serialize(object);
            jedisCluster = getJedisCluster();
            jedisCluster.setex(getKey(object).getBytes("utf-8"), getExpireSeconds(seconds), bytes);
        } finally {
            release(jedisCluster);
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
        JedisCluster jedisCluster = null;
        try {
            jedisCluster = getJedisCluster();
            byte[] res = jedisCluster.get(key.getBytes("utf-8"));
            return SerializationUtil.deserialize(res, clazz);
        } finally {
            release(jedisCluster);
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
        JedisCluster jedisCluster = null;
        try {
            jedisCluster = getJedisCluster();
            String key = clazz.getName() + "_" + id;
            byte[] res = jedisCluster.get(key.getBytes("utf-8"));
            return SerializationUtil.deserialize(res, clazz);
        } finally {
            release(jedisCluster);
        }
    }


    public static void delObject(Object record) throws TesJedisException {
        JedisCluster jedisCluster = null;
        try {
            jedisCluster = getJedisCluster();
            String key = getKey(record);
            jedisCluster.del(key);
        } finally {
            release(jedisCluster);

        }
    }

}
