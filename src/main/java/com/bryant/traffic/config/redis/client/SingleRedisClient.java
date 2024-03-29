//package com.bryant.traffic.config.cache.client;
//
//import com.bryant.traffic.config.cache.BaseRedisProperties;
//import com.bryant.traffic.exception.RedisClientException;
//import com.bryant.traffic.response.ResponseCode;
//import com.bryant.traffic.utils.LoggerUtils;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.function.Function;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.lang.NonNull;
//import redis.clients.jedis.BinaryClient;
//import redis.clients.jedis.BitOP;
//import redis.clients.jedis.BitPosParams;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//import redis.clients.jedis.JedisPubSub;
//import redis.clients.jedis.Pipeline;
//import redis.clients.jedis.SortingParams;
//import redis.clients.jedis.Tuple;
//
//@Slf4j
//public class SingleRedisClient implements RedisClient{
//
//    protected SingleRedisClient() {
//    }
//
//    public SingleRedisClient(BaseRedisProperties properties) {
//        String ip = properties.getHost();
//        int port = properties.getPort();
//        int database = properties.getDatabase();
//        String password = properties.getPassword();
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxTotal(properties.getMaxTotal());
//        jedisPoolConfig.setMaxIdle(properties.getMaxIdle());
//        jedisPoolConfig.setMaxWaitMillis(properties.getMaxWaitMillis());
//        jedisPoolConfig.setTestOnBorrow(properties.getTestOnBorrow());
//        jedisPoolConfig.setMinIdle(properties.getMinIdle());
//        LoggerUtils.fmtInfo(log, "redis初始化开始. ip %s. port %s.", ip, port);
//        jedisPool = new JedisPool(jedisPoolConfig, ip, port, 10000, password, database);
//    }
//
//    protected JedisPool jedisPool = null;
//
//    @Override
//    public String get(String key) {
//        Jedis jedis = null;
//        String value = null;
//        try {
//            jedis = jedisPool.getResource();
//            value = jedis.get(key);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return value;
//    }
//
//    /**
//     * 通过key获取储存在redis中的value,自动转对象
//     *
//     * @param key
//     * @param t
//     * @return
//     * @author manddoxli
//     * @date 2020/3/12
//     */
//    @Override
//    public <T> T get(String key, Class<T> t) {
//        String value = this.get(key);
//        return stringToBean(value, t);
//    }
//
//
//    @Override
//    public byte[] get(byte[] key) {
//        Jedis jedis = null;
//        byte[] value = null;
//        try {
//            jedis = jedisPool.getResource();
//            value = jedis.get(key);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return value;
//    }
//
//    @Override
//    public String set(String key, String value) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.set(key, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//
//
//    @Override
//    public String set(byte[] key, byte[] value) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.set(key, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//
//    @Override
//    public String set(String key, String value, String nxxx, String expx, long time) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.set(key, value, nxxx, expx, time);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//
//    /**
//     * <p>
//     * 删除指定的key,也可以传入一个包含key的数组
//     * </p>
//     *
//     * @param keys 一个key 也可以使 string 数组
//     * @return 返回删除成功的个数
//     */
//    @Override
//    public Long del(String... keys) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.del(keys);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//
//    /**
//     * <p>
//     * 通过key向指定的value值追加值
//     * </p>
//     *
//     * @param key
//     * @param str
//     * @return 成功返回 添加后value的长度 失败 返回 添加的 value 的长度 异常返回0L
//     */
//    @Override
//    public Long append(String key, String str) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.append(key, str);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 判断key是否存在
//     * </p>
//     *
//     * @param key
//     * @return true OR false
//     */
//    @Override
//    public Boolean exists(String key) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.exists(key);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//
//    /**
//     * <p>
//     * 清空当前数据库中的所有 key,此命令从不失败。
//     * </p>
//     *
//     * @return 总是返回 OK
//     */
////    @Override
////    public String flushDB() {
////        Jedis jedis = null;
////        try {
////            jedis = jedisPool.getResource();
////            return jedis.flushDB();
////        } catch (Exception e) {
////            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
////        } finally {
////            returnResource(jedisPool, jedis);
////        }
////    }
//
//
//    @Override
//    public Long expire(String key, int value) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.expire(key, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//
//
//    @Override
//    public Long ttl(String key) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.ttl(key);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//
//    /**
//     * <p>
//     * 移除给定 key 的生存时间，将这个 key 从『易失的』(带生存时间 key )转换成『持久的』(一个不带生存时间、永不过期的 key )
//     * </p>
//     *
//     * @param key
//     * @return 当生存时间移除成功时，返回 1 .如果 key 不存在或 key 没有设置生存时间，返回 0
//     */
//    @Override
//    public Long persist(String key) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.persist(key);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//
//    /**
//     * <p>
//     * 新增key,并将 key 的生存时间 (以秒为单位)
//     * </p>
//     *
//     * @param key
//     * @param seconds 生存时间 单位：秒
//     * @param value
//     * @return 设置成功时返回 OK 。当 seconds 参数不合法时，返回一个错误。
//     */
//    @Override
//    public String setex(String key, int seconds, String value) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.setex(key, seconds, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//
//    /**
//     * <p>
//     * 设置key value,如果key已经存在则返回0,nx==> not exist
//     * </p>
//     *
//     * @param key
//     * @param value
//     * @return 成功返回1 如果存在 和 发生异常 返回 0
//     */
//    @Override
//    public Long setnx(String key, String value) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.setnx(key, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//
//    /**
//     * <p>
//     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
//     * </p>
//     * <p>
//     * 当 key 存在但不是字符串类型时，返回一个错误。
//     * </p>
//     *
//     * @param key
//     * @param value
//     * @return 返回给定 key 的旧值。当 key 没有旧值时，也即是， key 不存在时，返回 nil
//     */
//    @Override
//    public String getSet(String key, String value) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.getSet(key, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//
//    /**
//     * <p>
//     * 设置key value并制定这个键值的有效期
//     * </p>
//     *
//     * @param key
//     * @param value
//     * @param seconds 单位:秒
//     * @return 成功返回OK 失败和异常返回null
//     */
//    @Override
//    public String setex(String key, String value, int seconds) {
//        Jedis jedis = null;
//        String res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.setex(key, seconds, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key 和offset 从指定的位置开始将原先value替换
//     * </p>
//     * <p>
//     * 下标从0开始,offset表示从offset下标开始替换
//     * </p>
//     *
//     * @param key
//     * @param str
//     * @param offset 下标位置
//     * @return 返回替换后 value 的长度
//     */
//    @Override
//    public Long setrange(String key, String str, int offset) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.setrange(key, offset, str);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//
//    /**
//     * <p>
//     * 通过批量的key获取批量的value
//     * </p>
//     *
//     * @param keys string数组 也可以是一个key
//     * @return 成功返回value的集合, 失败返回null的集合 ,异常返回空
//     */
//    @Override
//    public List<String> mget(String... keys) {
//        Jedis jedis = null;
//        List<String> values = null;
//        try {
//            jedis = jedisPool.getResource();
//            values = jedis.mget(keys);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return values;
//    }
//
//    /**
//     * <p>
//     * 批量的设置key:value,可以一个
//     * </p>
//     * <p>
//     * example:
//     * </p>
//     * <p>
//     * obj.mset(new String[]{"key2","value1","key2","value2"})
//     * </p>
//     *
//     * @param keysvalues
//     * @return 成功返回OK 失败 异常 返回 null
//     */
//    @Override
//    public String mset(String... keysvalues) {
//        Jedis jedis = null;
//        String res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.mset(keysvalues);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 批量的设置key:value,可以一个,如果key已经存在则会失败,操作会回滚
//     * </p>
//     * <p>
//     * example:
//     * </p>
//     * <p>
//     * obj.msetnx(new String[]{"key2","value1","key2","value2"})
//     * </p>
//     *
//     * @param keysvalues
//     * @return 成功返回1 失败返回0
//     */
//    @Override
//    public Long msetnx(String... keysvalues) {
//        Jedis jedis = null;
//        Long res = 0L;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.msetnx(keysvalues);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过下标 和key 获取指定下标位置的 value
//     * </p>
//     *
//     * @param key
//     * @param startOffset 开始位置 从0 开始 负数表示从右边开始截取
//     * @param endOffset
//     * @return 如果没有返回null
//     */
//    @Override
//    public String getrange(String key, int startOffset, int endOffset) {
//        Jedis jedis = null;
//        String res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.getrange(key, startOffset, endOffset);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1
//     * </p>
//     *
//     * @param key
//     * @return 加值后的结果
//     */
//    @Override
//    public Long incr(String key) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.incr(key);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key给指定的value加值,如果key不存在,则这是value为该值
//     * </p>
//     *
//     * @param key
//     * @param incr
//     * @return
//     */
//    @Override
//    public Long incrBy(String key, Long incr) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.incrBy(key, incr);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 对key的值做减减操作,如果key不存在,则设置key为-1
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    @Override
//    public Long decr(String key) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.decr(key);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 减去指定的值
//     * </p>
//     *
//     * @param key
//     * @param decr
//     * @return
//     */
//    @Override
//    public Long decrBy(String key, Long decr) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.decrBy(key, decr);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key获取value值的长度
//     * </p>
//     *
//     * @param key
//     * @return 失败返回null
//     */
//    @Override
//    public Long strlen(String key) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.strlen(key);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key给field设置指定的值,如果key不存在,则先创建
//     * </p>
//     *
//     * @param key
//     * @param field 字段
//     * @param value
//     * @return 如果存在返回0 异常返回null
//     */
//    @Override
//    public Long hset(String key, String field, String value) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.hset(key, field, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key给field设置指定的值,如果key不存在则先创建,如果field已经存在,返回0
//     * </p>
//     *
//     * @param key
//     * @param field
//     * @param value
//     * @return
//     */
//    @Override
//    public Long hsetnx(String key, String field, String value) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.hsetnx(key, field, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//
//    @Override
//    public String hmset(String key, Map<String, String> hash) {
//        Jedis jedis = null;
//        String res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.hmset(key, hash);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key 和 field 获取指定的 value
//     * </p>
//     *
//     * @param key
//     * @param field
//     * @return 没有返回null
//     */
//    @Override
//    public String hget(String key, String field) {
//        Jedis jedis = null;
//        String res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.hget(key, field);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//
//    @Override
//    public List<String> hmget(String key, String... fields) {
//        Jedis jedis = null;
//        List<String> res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.hmget(key, fields);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key给指定的field的value加上给定的值
//     * </p>
//     *
//     * @param key
//     * @param field
//     * @param value
//     * @return
//     */
//    @Override
//    public Long hincrby(String key, String field, Long value) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.hincrBy(key, field, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key和field判断是否有指定的value存在
//     * </p>
//     *
//     * @param key
//     * @param field
//     * @return
//     */
//    @Override
//    public Boolean hexists(String key, String field) {
//        Jedis jedis = null;
//        Boolean res = false;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.hexists(key, field);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key返回field的数量
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    @Override
//    public Long hlen(String key) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.hlen(key);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//
//    }
//
//    /**
//     * <p>
//     * 通过key 删除指定的 field
//     * </p>
//     *
//     * @param key
//     * @param fields 可以是 一个 field 也可以是 一个数组
//     * @return
//     */
//    @Override
//    public Long hdel(String key, String... fields) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.hdel(key, fields);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key返回所有的field
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    @Override
//    public Set<String> hkeys(String key) {
//        Jedis jedis = null;
//        Set<String> res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.hkeys(key);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key返回所有和key有关的value
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    @Override
//    public List<String> hvals(String key) {
//        Jedis jedis = null;
//        List<String> res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.hvals(key);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//
//    @Override
//    public Map<String, String> hgetall(String key) {
//        Jedis jedis = null;
//        Map<String, String> res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.hgetAll(key);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    @Override
//    public Long lpush(String key, String... strs) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.lpush(key, strs);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key向list尾部添加字符串
//     * </p>
//     *
//     * @param key
//     * @param strs 可以使一个string 也可以使string数组
//     * @return 返回list的value个数
//     */
//    @Override
//    public Long rpush(String key, String... strs) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.rpush(key, strs);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key在list指定的位置之前或者之后 添加字符串元素
//     * </p>
//     *
//     * @param key
//     * @param where LIST_POSITION枚举类型
//     * @param pivot list里面的value
//     * @param value 添加的value
//     * @return
//     */
//    @Override
//    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot,
//                        String value) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.linsert(key, where, pivot, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key设置list指定下标位置的value
//     * </p>
//     * <p>
//     * 如果下标超过list里面value的个数则报错
//     * </p>
//     *
//     * @param key
//     * @param index 从0开始
//     * @param value
//     * @return 成功返回OK
//     */
//    @Override
//    public String lset(String key, Long index, String value) {
//        Jedis jedis = null;
//        String res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.lset(key, index, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key从对应的list中删除指定的count个 和 value相同的元素
//     * </p>
//     *
//     * @param key
//     * @param count 当count为0时删除全部
//     * @param value
//     * @return 返回被删除的个数
//     */
//    @Override
//    public Long lrem(String key, long count, String value) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.lrem(key, count, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key保留list中从strat下标开始到end下标结束的value值
//     * </p>
//     *
//     * @param key
//     * @param start
//     * @param end
//     * @return 成功返回OK
//     */
//    @Override
//    public String ltrim(String key, long start, long end) {
//        Jedis jedis = null;
//        String res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.ltrim(key, start, end);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key从list的头部删除一个value,并返回该value
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    @Override
//    public String lpop(String key) {
//        Jedis jedis = null;
//        String res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.lpop(key);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//
//    @Override
//    public String rpop(String key) {
//        Jedis jedis = null;
//        String res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.rpop(key);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//
//    @Override
//    public String rpoplpush(String srckey, String dstkey) {
//        Jedis jedis = null;
//        String res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.rpoplpush(srckey, dstkey);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key获取list中指定下标位置的value
//     * </p>
//     *
//     * @param key
//     * @param index
//     * @return 如果没有返回null
//     */
//    @Override
//    public String lindex(String key, long index) {
//        Jedis jedis = null;
//        String res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.lindex(key, index);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key返回list的长度
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    @Override
//    public Long llen(String key) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.llen(key);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//
//    @Override
//    public List<String> lrange(String key, long start, long end) {
//        Jedis jedis = null;
//        List<String> res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.lrange(key, start, end);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 将列表 key 下标为 index 的元素的值设置为 value
//     * </p>
//     *
//     * @param key
//     * @param index
//     * @param value
//     * @return 操作成功返回 ok ，否则返回错误信息
//     */
//    @Override
//    public String lset(String key, long index, String value) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.lset(key, index, value);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//
//    /**
//     * <p>
//     * 返回给定排序后的结果
//     * </p>
//     *
//     * @param key
//     * @param sortingParameters
//     * @return 返回列表形式的排序结果
//     */
//    @Override
//    public List<String> sort(String key, SortingParams sortingParameters) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.sort(key, sortingParameters);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//
//    /**
//     * <p>
//     * 返回排序后的结果，排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较。
//     * </p>
//     *
//     * @param key
//     * @return 返回列表形式的排序结果
//     */
//    @Override
//    public List<String> sort(String key) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.sort(key);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//
//    /**
//     * <p>
//     * 通过key向指定的set中添加value
//     * </p>
//     *
//     * @param key
//     * @param members 可以是一个String 也可以是一个String数组
//     * @return 添加成功的个数
//     */
//    @Override
//    public Long sadd(String key, String... members) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.sadd(key, members);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key删除set中对应的value值
//     * </p>
//     *
//     * @param key
//     * @param members 可以是一个String 也可以是一个String数组
//     * @return 删除的个数
//     */
//    @Override
//    public Long srem(String key, String... members) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.srem(key, members);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key随机删除一个set中的value并返回该值
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    @Override
//    public String spop(String key) {
//        Jedis jedis = null;
//        String res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.spop(key);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key随机删除一个set中的value并返回该值
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    @Override
//    public Set<String> spop(String key, long count) {
//        Jedis jedis = null;
//        Set<String> res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.spop(key, count);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key获取set中的差集
//     * </p>
//     * <p>
//     * 以第一个set为标准
//     * </p>
//     *
//     * @param keys 可以使一个string 则返回set中所有的value 也可以是string数组
//     * @return
//     */
//    @Override
//    public Set<String> sdiff(String... keys) {
//        Jedis jedis = null;
//        Set<String> res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.sdiff(keys);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key获取set中的差集并存入到另一个key中
//     * </p>
//     * <p>
//     * 以第一个set为标准
//     * </p>
//     *
//     * @param dstkey 差集存入的key
//     * @param keys   可以使一个string 则返回set中所有的value 也可以是string数组
//     * @return
//     */
//    @Override
//    public Long sdiffstore(String dstkey, String... keys) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.sdiffstore(dstkey, keys);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key获取指定set中的交集
//     * </p>
//     *
//     * @param keys 可以使一个string 也可以是一个string数组
//     * @return
//     */
//    @Override
//    public Set<String> sinter(String... keys) {
//        Jedis jedis = null;
//        Set<String> res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.sinter(keys);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key获取指定set中的交集 并将结果存入新的set中
//     * </p>
//     *
//     * @param dstkey
//     * @param keys   可以使一个string 也可以是一个string数组
//     * @return
//     */
//    @Override
//    public Long sinterstore(String dstkey, String... keys) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.sinterstore(dstkey, keys);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key返回所有set的并集
//     * </p>
//     *
//     * @param keys 可以使一个string 也可以是一个string数组
//     * @return
//     */
//    @Override
//    public Set<String> sunion(String... keys) {
//        Jedis jedis = null;
//        Set<String> res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.sunion(keys);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key返回所有set的并集,并存入到新的set中
//     * </p>
//     *
//     * @param dstkey
//     * @param keys   可以使一个string 也可以是一个string数组
//     * @return
//     */
//    @Override
//    public Long sunionstore(String dstkey, String... keys) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.sunionstore(dstkey, keys);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key将set中的value移除并添加到第二个set中
//     * </p>
//     *
//     * @param srckey 需要移除的
//     * @param dstkey 添加的
//     * @param member set中的value
//     * @return
//     */
//    @Override
//    public Long smove(String srckey, String dstkey, String member) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.smove(srckey, dstkey, member);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key获取set中value的个数
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    @Override
//    public Long scard(String key) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.scard(key);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key判断value是否是set中的元素
//     * </p>
//     *
//     * @param key
//     * @param member
//     * @return
//     */
//    @Override
//    public Boolean sismember(String key, String member) {
//        Jedis jedis = null;
//        Boolean res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.sismember(key, member);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key获取set中随机的value,不删除元素
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    @Override
//    public String srandmember(String key) {
//        Jedis jedis = null;
//        String res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.srandmember(key);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key获取set中所有的value
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    @Override
//    public Set<String> smembers(String key) {
//        Jedis jedis = null;
//        Set<String> res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.smembers(key);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key向zset中添加value,score,其中score就是用来排序的
//     * </p>
//     * <p>
//     * 如果该value已经存在则根据score更新元素
//     * </p>
//     *
//     * @param key
//     * @param score
//     * @param member
//     * @return
//     */
//    @Override
//    public Long zadd(String key, double score, String member) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.zadd(key, score, member);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 返回有序集 key 中，指定区间内的成员。min=0,max=-1代表所有元素
//     * </p>
//     *
//     * @param key
//     * @param min
//     * @param max
//     * @return 指定区间内的有序集成员的列表。
//     */
//    @Override
//    public Set<String> zrange(String key, long min, long max) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.zrange(key, min, max);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//
//    /**
//     * <p>
//     * 统计有序集 key 中,值在 min 和 max 之间的成员的数量
//     * </p>
//     *
//     * @param key
//     * @param min
//     * @param max
//     * @return 值在 min 和 max 之间的成员的数量。异常返回0
//     */
//    @Override
//    public Long zcount(String key, double min, double max) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.zcount(key, min, max);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//
//    }
//
//    /**
//     * <p>
//     * 为哈希表 key 中的域 field 的值加上增量 increment 。增量也可以为负数，相当于对给定域进行减法操作。 如果 key
//     * 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
//     * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。本操作的值被限制在 64 位(bit)有符号数字表示之内。
//     * </p>
//     * <p>
//     * 将名称为key的hash中field的value增加integer
//     * </p>
//     *
//     * @param key
//     * @param field
//     * @param increment
//     * @return 执行 HINCRBY 命令之后，哈希表 key 中域 field的值。异常返回0
//     */
//    @Override
//    public Long hincrBy(String key, String field, long increment) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.hincrBy(key, field, increment);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//
//    }
//
//    /**
//     * <p>
//     * 通过key删除在zset中指定的value
//     * </p>
//     *
//     * @param key
//     * @param members 可以使一个string 也可以是一个string数组
//     * @return
//     */
//    @Override
//    public Long zrem(String key, String... members) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.zrem(key, members);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key增加该zset中value的score的值
//     * </p>
//     *
//     * @param key
//     * @param score
//     * @param member
//     * @return
//     */
//    @Override
//    public Double zincrby(String key, double score, String member) {
//        Jedis jedis = null;
//        Double res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.zincrby(key, score, member);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key返回zset中value的排名
//     * </p>
//     * <p>
//     * 下标从小到大排序
//     * </p>
//     *
//     * @param key
//     * @param member
//     * @return
//     */
//    @Override
//    public Long zrank(String key, String member) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.zrank(key, member);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key返回zset中value的排名
//     * </p>
//     * <p>
//     * 下标从大到小排序
//     * </p>
//     *
//     * @param key
//     * @param member
//     * @return
//     */
//    @Override
//    public Long zrevrank(String key, String member) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.zrevrank(key, member);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key将获取score从start到end中zset的value
//     * </p>
//     * <p>
//     * socre从大到小排序
//     * </p>
//     * <p>
//     * 当start为0 end为-1时返回全部
//     * </p>
//     *
//     * @param key
//     * @param start
//     * @param end
//     * @return
//     */
//    @Override
//    public Set<String> zrevrange(String key, long start, long end) {
//        Jedis jedis = null;
//        Set<String> res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.zrevrange(key, start, end);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key返回指定score内zset中的value
//     * </p>
//     *
//     * @param key
//     * @param max
//     * @param min
//     * @return
//     */
//    @Override
//    public Set<String> zrangebyScore(String key, String max, String min) {
//        Jedis jedis = null;
//        Set<String> res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.zrangeByScore(key, min, max);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    @Override
//    public Set<String> zrevrangeByScore(String key, double max, double min) {
//        Jedis jedis = null;
//        Set<String> res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.zrevrangeByScore(key, max, min);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    @Override
//    public Set<String> zrevrangeByScore(String key, String max, String min) {
//        Jedis jedis = null;
//        Set<String> res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.zrevrangeByScore(key, max, min);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    @Override
//    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
//        Jedis jedis = null;
//        Set<Tuple> res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key返回指定score内zset中的value
//     * </p>
//     *
//     * @param key
//     * @param max
//     * @param min
//     * @return
//     */
//    @Override
//    public Set<String> zrangeByScore(String key, double max, double min) {
//        Jedis jedis = null;
//        Set<String> res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.zrangeByScore(key, min, max);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key返回指定score内zset中的value
//     * </p>
//     *
//     * @param key
//     * @param max
//     * @param min
//     * @return
//     */
//    @Override
//    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
//        Jedis jedis = null;
//        Set<Tuple> res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.zrangeByScoreWithScores(key, min, max, offset, count);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 返回指定区间内zset中value的数量
//     * </p>
//     *
//     * @param key
//     * @param min
//     * @param max
//     * @return
//     */
//    @Override
//    public Long zcount(String key, String min, String max) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.zcount(key, min, max);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key返回zset中的value个数
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    @Override
//    public Long zcard(String key) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.zcard(key);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key获取zset中value的score值
//     * </p>
//     *
//     * @param key
//     * @param member
//     * @return
//     */
//    @Override
//    public Double zscore(String key, String member) {
//        Jedis jedis = null;
//        Double res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.zscore(key, member);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key删除给定区间内的元素
//     * </p>
//     *
//     * @param key
//     * @param start
//     * @param end
//     * @return
//     */
//    @Override
//    public Long zremrangeByRank(String key, long start, long end) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.zremrangeByRank(key, start, end);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    /**
//     * <p>
//     * 通过key删除指定score内的元素
//     * </p>
//     *
//     * @param key
//     * @param start
//     * @param end
//     * @return
//     */
//    @Override
//    public Long zremrangeByScore(String key, double start, double end) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.zremrangeByScore(key, start, end);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//
//    /**
//     * <p>
//     * 通过key判断值得类型
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    @Override
//    public String type(String key) {
//        Jedis jedis = null;
//        String res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.type(key);
//        } catch (Exception e) {
//
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return res;
//    }
//
//    @Override
//    public <T> T pipelined(@NonNull Function<Pipeline, T> action) {
//        Jedis jedis = jedisPool.getResource();
//        T apply;
//        try {
//            Pipeline pipelined = jedis.pipelined();
//            apply = action.apply(pipelined);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//        return apply;
//    }
//
//    /**
//     * 返还到连接池
//     *
//     * @param jedisPool
//     * @param jedis
//     */
//    public static void returnResource(JedisPool jedisPool, Jedis jedis) {
//        if (jedis != null) {
//            jedis.close();
//        }
//    }
//
//    /**-------位图相关--------**/
//
//    @Override
//    public Boolean setBit(String key, long offset, String value) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.setbit(key, offset, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//    @Override
//    public Boolean setBit(String key, long offset, boolean value) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.setbit(key, offset, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//    @Override
//    public Boolean setBit(byte[] key, long offset, byte[] value) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.setbit(key, offset, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//    @Override
//    public Boolean setBit(byte[] key, long offset, boolean value) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.setbit(key, offset, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//    @Override
//    public Boolean getBit(byte[] key, long offset) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.getbit(key, offset);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//    @Override
//    public Boolean getBit(String key, long offset) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.getbit(key, offset);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//    @Override
//    public Long bitCount(String key) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.bitcount(key);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//    @Override
//    public Long bitCount(String key, long start, long end) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.bitcount(key, start, end);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//    @Override
//    public Long bitPos(String key, boolean value) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.bitpos(key, value);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//    @Override
//    public Long bitPos(String key, boolean value, long start, long end) {
//        Jedis jedis = null;
//        BitPosParams bitPosParams = new BitPosParams(start, end);
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.bitpos(key, value, bitPosParams);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//    @Override
//    public Long bitOp(BitOP bitOP, String destKey, String[] keys) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            return jedis.bitop(bitOP, destKey, keys);
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//
//    /**
//     * 发布消息
//     *
//     * @param channel
//     * @param message
//     * @return
//     */
//    @Override
//    public Long publish(String channel, String message) {
//        Jedis jedis = null;
//        Long res = null;
//        try {
//            jedis = jedisPool.getResource();
//            res = jedis.publish(channel, message);
//
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//
//        return res;
//    }
//
//    /**
//     * 订阅消息
//     *
//     * @param jedisPubSub
//     * @param channels
//     */
//    @Override
//    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            jedis.subscribe(jedisPubSub, channels);
//
//        } catch (Exception e) {
//            throw new RedisClientException(ResponseCode.REDIS_COMMON_EXCEPTION.getMsg() + e.getMessage(), e);
//
//        } finally {
//            returnResource(jedisPool, jedis);
//        }
//    }
//}
