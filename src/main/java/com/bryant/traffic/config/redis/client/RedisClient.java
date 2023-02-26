//package com.bryant.traffic.config.cache.client;
//
//import com.bryant.traffic.utils.JsonUtils;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.function.Function;
//import redis.clients.jedis.*;
//
//public interface RedisClient {
//
//
//    /**
//     * <p>
//     * 通过key获取储存在redis中的value
//     * </p>
//     * <p>
//     * 并释放连接
//     * </p>
//     *
//     * @param key
//     * @return 成功返回value 失败返回null
//     */
//    String get(String key);
//
//    /**
//     * 通过key获取储存在redis中的value,自动转对象
//     *
//     * @param key
//     * @return
//     * @author manddoxli
//     * @date 2020/3/12
//     */
//    <T> T get(String key, Class<T> t);
//
//
//    /**
//     * <p>
//     * 通过key获取储存在redis中的value
//     * </p>
//     * <p>
//     * 并释放连接
//     * </p>
//     *
//     * @param key
//     * @return 成功返回value 失败返回null
//     */
//    byte[] get(byte[] key);
//
//
//    /**
//     * <p>
//     * 向redis存入key和value,并释放连接资源
//     * </p>
//     * <p>
//     * 如果key已经存在 则覆盖
//     * </p>
//     *
//     * @param key
//     * @param value
//     * @return 成功 返回OK 失败返回 0
//     */
//    String set(String key, String value);
//
//
//    /**
//     * <p>
//     * 向redis存入key和value,并释放连接资源
//     * </p>
//     * <p>
//     * 如果key已经存在 则覆盖
//     * </p>
//     *
//     * @param key
//     * @param value
//     * @return 成功 返回OK 失败返回 0
//     */
//    String set(byte[] key, byte[] value);
//
//
//    /**
//     * Set the string value as value of the key. The string can't be longer than 1073741824 bytes (1
//     * GB).
//     *
//     * @param key
//     * @param value
//     * @param nxxx  NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key
//     *              if it already exist.
//     * @param expx  EX|PX, expire time units: EX = seconds; PX = milliseconds
//     * @param time  expire time in the units of <code>expx</code>
//     * @return Status code reply
//     */
//    String set(final String key, final String value, final String nxxx, final String expx,
//               final long time);
//
//    /**
//     * <p>
//     * 删除指定的key,也可以传入一个包含key的数组
//     * </p>
//     *
//     * @param keys 一个key 也可以使 string 数组
//     * @return 返回删除成功的个数
//     */
//    Long del(String... keys);
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
//    Long append(String key, String str);
//
//    /**
//     * <p>
//     * 判断key是否存在
//     * </p>
//     *
//     * @param key
//     * @return true OR false
//     */
//    Boolean exists(String key);
//
//    /**
//     * <p>
//     * 清空当前数据库中的所有 key,此命令从不失败。
//     * </p>
//     *
//     * @return 总是返回 OK
//     */
////    String flushDB();
//
//
//    /**
//     * <p>
//     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
//     * </p>
//     *
//     * @param key
//     * @param value 过期时间，单位：秒
//     * @return 成功返回1 如果存在 和 发生异常 返回 0
//     */
//    Long expire(String key, int value);
//
//
//    /**
//     * <p>
//     * 以秒为单位，返回给定 key 的剩余生存时间
//     * </p>
//     *
//     * @param key
//     * @return 当 key 不存在时，返回 -2 。当 key 存在但没有设置剩余生存时间时，返回 -1 。否则，以秒为单位，返回 key
//     * 的剩余生存时间。 发生异常 返回 0
//     */
//    Long ttl(String key);
//
//    /**
//     * <p>
//     * 移除给定 key 的生存时间，将这个 key 从『易失的』(带生存时间 key )转换成『持久的』(一个不带生存时间、永不过期的 key )
//     * </p>
//     *
//     * @param key
//     * @return 当生存时间移除成功时，返回 1 .如果 key 不存在或 key 没有设置生存时间，返回 0
//     */
//    Long persist(String key);
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
//    String setex(String key, int seconds, String value);
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
//    Long setnx(String key, String value);
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
//    String getSet(String key, String value);
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
//    String setex(String key, String value, int seconds);
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
//    Long setrange(String key, String str, int offset);
//
//    /**
//     * <p>
//     * 通过批量的key获取批量的value
//     * </p>
//     *
//     * @param keys string数组 也可以是一个key
//     * @return 成功返回value的集合, 失败返回null的集合 ,异常返回空
//     */
//    List<String> mget(String... keys);
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
//    String mset(String... keysvalues);
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
//    Long msetnx(String... keysvalues);
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
//    String getrange(String key, int startOffset, int endOffset);
//
//    /**
//     * <p>
//     * 通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1
//     * </p>
//     *
//     * @param key
//     * @return 加值后的结果
//     */
//    Long incr(String key);
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
//    Long incrBy(String key, Long incr);
//
//    /**
//     * <p>
//     * 对key的值做减减操作,如果key不存在,则设置key为-1
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    Long decr(String key);
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
//    Long decrBy(String key, Long decr);
//
//    /**
//     * <p>
//     * 通过key获取value值的长度
//     * </p>
//     *
//     * @param key
//     * @return 失败返回null
//     */
//    Long strlen(String key);
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
//    Long hset(String key, String field, String value);
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
//    Long hsetnx(String key, String field, String value);
//
//
//    /**
//     * <p>
//     * 通过key同时设置 hash的多个field
//     * </p>
//     *
//     * @param key
//     * @param hash
//     * @return 返回OK 异常返回null
//     */
//    String hmset(String key, Map<String, String> hash);
//
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
//    String hget(String key, String field);
//
//
//    /**
//     * <p>
//     * 通过key 和 fields 获取指定的value 如果没有对应的value则返回null
//     * </p>
//     *
//     * @param key
//     * @param fields 可以使 一个String 也可以是 String数组
//     * @return
//     */
//    List<String> hmget(String key, String... fields);
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
//    Long hincrby(String key, String field, Long value);
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
//    Boolean hexists(String key, String field);
//
//    /**
//     * <p>
//     * 通过key返回field的数量
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    Long hlen(String key);
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
//    Long hdel(String key, String... fields);
//
//    /**
//     * <p>
//     * 通过key返回所有的field
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    Set<String> hkeys(String key);
//
//    /**
//     * <p>
//     * 通过key返回所有和key有关的value
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    List<String> hvals(String key);
//
//
//    /**
//     * <p>
//     * 通过key获取所有的field和value
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    Map<String, String> hgetall(String key);
//
//
//    /**
//     * <p>
//     * 通过key向list头部添加字符串
//     * </p>
//     *
//     * @param key
//     * @param strs 可以使一个string 也可以使string数组
//     * @return 返回list的value个数
//     */
//    Long lpush(String key, String... strs);
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
//    Long rpush(String key, String... strs);
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
//    Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot,
//                 String value);
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
//    String lset(String key, Long index, String value);
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
//    Long lrem(String key, long count, String value);
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
//    String ltrim(String key, long start, long end);
//
//    /**
//     * <p>
//     * 通过key从list的头部删除一个value,并返回该value
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    String lpop(String key);
//
//
//    /**
//     * <p>
//     * 通过key从list尾部删除一个value,并返回该元素
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    String rpop(String key);
//
//
//    /**
//     * <p>
//     * 通过key从一个list的尾部删除一个value并添加到另一个list的头部,并返回该value
//     * </p>
//     * <p>
//     * 如果第一个list为空或者不存在则返回null
//     * </p>
//     *
//     * @param srckey
//     * @param dstkey
//     * @return
//     */
//    String rpoplpush(String srckey, String dstkey);
//
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
//    String lindex(String key, long index);
//
//    /**
//     * <p>
//     * 通过key返回list的长度
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    Long llen(String key);
//
//
//    /**
//     * <p>
//     * 通过key获取list指定下标位置的value
//     * </p>
//     * <p>
//     * 如果start 为 0 end 为 -1 则返回全部的list中的value
//     * </p>
//     *
//     * @param key
//     * @param start
//     * @param end
//     * @return
//     */
//    List<String> lrange(String key, long start, long end);
//
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
//    String lset(String key, long index, String value);
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
//    List<String> sort(String key, SortingParams sortingParameters);
//
//    /**
//     * <p>
//     * 返回排序后的结果，排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较。
//     * </p>
//     *
//     * @param key
//     * @return 返回列表形式的排序结果
//     */
//    List<String> sort(String key);
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
//    Long sadd(String key, String... members);
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
//    Long srem(String key, String... members);
//
//    /**
//     * <p>
//     * 通过key随机删除一个set中的value并返回该值
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    String spop(String key);
//
//    /**
//     * <p>
//     * 通过key随机删除一个set中的value并返回该值
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    Set<String> spop(String key, long count);
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
//    Set<String> sdiff(String... keys);
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
//    Long sdiffstore(String dstkey, String... keys);
//
//    /**
//     * <p>
//     * 通过key获取指定set中的交集
//     * </p>
//     *
//     * @param keys 可以使一个string 也可以是一个string数组
//     * @return
//     */
//    Set<String> sinter(String... keys);
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
//    Long sinterstore(String dstkey, String... keys);
//
//    /**
//     * <p>
//     * 通过key返回所有set的并集
//     * </p>
//     *
//     * @param keys 可以使一个string 也可以是一个string数组
//     * @return
//     */
//    Set<String> sunion(String... keys);
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
//    Long sunionstore(String dstkey, String... keys);
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
//    Long smove(String srckey, String dstkey, String member);
//
//    /**
//     * <p>
//     * 通过key获取set中value的个数
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    Long scard(String key);
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
//    Boolean sismember(String key, String member);
//
//    /**
//     * <p>
//     * 通过key获取set中随机的value,不删除元素
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    String srandmember(String key);
//
//    /**
//     * <p>
//     * 通过key获取set中所有的value
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    Set<String> smembers(String key);
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
//    Long zadd(String key, double score, String member);
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
//    Set<String> zrange(String key, long min, long max);
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
//    Long zcount(String key, double min, double max);
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
//    Long hincrBy(String key, String field, long increment);
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
//    Long zrem(String key, String... members);
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
//    Double zincrby(String key, double score, String member);
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
//    Long zrank(String key, String member);
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
//    Long zrevrank(String key, String member);
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
//    Set<String> zrevrange(String key, long start, long end);
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
//    Set<String> zrangebyScore(String key, String max, String min);
//
//    /**
//     * <p>
//     * 通过key返回指定score内zset中的value，逆序
//     * </p>
//     *
//     * @param key
//     * @param max
//     * @param min
//     * @return
//     */
//    Set<String> zrevrangeByScore(String key, double max, double min);
//
//    /**
//     * <p>
//     * 通过key返回指定score内zset中的value，逆序
//     * </p>
//     *
//     * @param key
//     * @param max
//     * @param min
//     * @return
//     */
//    Set<String> zrevrangeByScore(String key, String max, String min);
//
//    /**
//     * <p>
//     * 通过key返回指定score内zset中的value及score，逆序
//     * </p>
//     *
//     * @param key
//     * @param max
//     * @param min
//     * @return
//     */
//    Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count);
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
//    Set<String> zrangeByScore(String key, double max, double min);
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
//    Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count);
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
//    Long zcount(String key, String min, String max);
//
//    /**
//     * <p>
//     * 通过key返回zset中的value个数
//     * </p>
//     *
//     * @param key
//     * @return
//     */
//    Long zcard(String key);
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
//    Double zscore(String key, String member);
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
//    Long zremrangeByRank(String key, long start, long end);
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
//    Long zremrangeByScore(String key, double start, double end);
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
//    String type(String key);
//
//
//    <T> T pipelined(Function<Pipeline, T> action);
//
//    /**
//     * 发布消息
//     *
//     * @param channel
//     * @param message
//     * @return
//     */
//    Long publish(String channel, String message);
//
//    /**
//     * 订阅消息
//     *
//     * @param jedisPubSub
//     * @param channels
//     */
//    void subscribe(JedisPubSub jedisPubSub, String... channels);
//
//    /**
//     * 设置某一位上的值
//     * @param key
//     * @param offset offset位偏移量，从0开始，最大不超过4294967295L
//     * @param value
//     * @return
//     */
//    Boolean setBit(String key, long offset, String value);
//
//    /**
//     * 设置某一位上的值
//     * @param key
//     * @param offset offset位偏移量，从0开始，最大不超过4294967295L
//     * @param value
//     * @return
//     */
//    Boolean setBit(String key, long offset, boolean value);
//    /**
//     * 设置某一位上的值
//     * @param key
//     * @param offset offset位偏移量，从0开始，最大不超过4294967295L
//     * @param value
//     * @return
//     */
//    Boolean setBit(byte[] key, long offset, byte[] value);
//
//    /**
//     * 设置某一位上的值
//     * @param key
//     * @param offset offset位偏移量，从0开始，最大不超过4294967295L
//     * @param value
//     * @return
//     */
//    Boolean setBit(byte[] key, long offset, boolean value);
//
//    /**
//     * 获取某一位上的值
//     * @param key
//     * @param offset offset位偏移量，从0开始，最大不超过4294967295L
//     * @return 1：true,0: false
//     */
//    Boolean getBit(byte[] key, long offset);
//
//    /**
//     * 获取某一位上的值
//     * @param key
//     * @param offset offset位偏移量，从0开始，最大不超过4294967295L
//     * @return 1：true,0: false
//     */
//    Boolean getBit(String key, long offset);
//
//    /**
//     * 统计key上值为1的个数
//     * @param key
//     * @return
//     */
//    Long bitCount(String key);
//
//    /**
//     * 统计指定位区间上值为1的个数
//     * @param key
//     * @param start
//     * @param end
//     * @return
//     */
//    Long bitCount(String key, long start, long end);
//
//    /**
//     * 统计首次出现的0或1的bit位
//     * @param key
//     * @param value
//     * @return
//     */
//    Long bitPos(String key, boolean value);
//
//    /**
//     * 统计区间内首次出现的0或1的bit位
//     * @param key
//     * @param value
//     * @param start
//     * @param end
//     * @return
//     */
//    Long bitPos(String key, boolean value, long start, long end);
//
//    /**
//     * 位操作
//     * @param bitOP
//     * @param destKey
//     * @param keys
//     * @return
//     */
//    Long bitOp(BitOP bitOP, String destKey, String[] keys);
//
//
//    /**
//     * 序列化对象
//     *
//     * @param obj
//     * @return 对象需实现Serializable接口
//     */
//    static byte[] ObjTOSerialize(Object obj) {
//        ObjectOutputStream oos = null;
//        ByteArrayOutputStream byteOut = null;
//        try {
//            byteOut = new ByteArrayOutputStream();
//            oos = new ObjectOutputStream(byteOut);
//            oos.writeObject(obj);
//            byte[] bytes = byteOut.toByteArray();
//            return bytes;
//        } catch (Exception e) {
//        }
//        return null;
//    }
//
//    /**
//     * 反序列化对象
//     *
//     * @param bytes
//     * @return 对象需实现Serializable接口
//     */
//    static Object unserialize(byte[] bytes) {
//        ByteArrayInputStream bais = null;
//        try {
//            //反序列化
//            bais = new ByteArrayInputStream(bytes);
//            ObjectInputStream ois = new ObjectInputStream(bais);
//            return ois.readObject();
//        } catch (Exception e) {
//        }
//        return null;
//    }
//
//    /**
//     * 返还到连接池
//     *
//     * @param jedisPool
//     * @param jedis
//     */
//    static void returnResource(JedisPool jedisPool, Jedis jedis) {
//        if (jedis != null) {
//            jedis.close();
//        }
//    }
//
//    default <T> T stringToBean(String value, Class<T> clazz) {
//        if (value == null || value.length() <= 0 || clazz == null) {
//            return null;
//        }
//
//        if (clazz == int.class || clazz == Integer.class) {
//            return (T) Integer.valueOf(value);
//        } else if (clazz == long.class || clazz == Long.class) {
//            return (T) Long.valueOf(value);
//        } else if (clazz == String.class) {
//            return (T) value;
//        } else {
//            return JsonUtils.json2Object(value, clazz);
//        }
//    }
//
//}
