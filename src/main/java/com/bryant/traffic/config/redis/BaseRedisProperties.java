//package com.bryant.traffic.config.cache;
//
//import com.bryant.traffic.utils.LoggerUtils;
//import java.util.HashSet;
//import java.util.Set;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
////import redis.clients.jedis.HostAndPort;
//
//@Slf4j
//@Data
//public class BaseRedisProperties {
//
//    Integer database = 0;
//
//    /**
//     * address的配置格式为：host1:port1,host2:port2
//     * address的配置与host、port互斥，二者配置其中一个即可，当address为空时，取host：port作为缺省值
//     */
//    String address = "";
//
//    /**
//     * host
//     */
//    String host = "localhost";
//
//    /**
//     * port
//     */
//    Integer port = 6379;
//    /**
//     * password
//     */
//    String password = null;
//
//    /**
//     * 最大连接数
//     */
//    Integer maxTotal = 20;
//    /**
//     * 最大空闲连接数
//     */
//    Integer maxIdle = 20;
//
//    /**
//     * 最小连接数
//     */
//    Integer minIdle = 15;
//
//    /**
//     * 获取连接时的最大等待毫秒数,小于零:阻塞不确定的时间
//     */
//    Long maxWaitMillis = 1000L;
//    /**
//     * 在获取连接的时候检查有效性
//     */
//    Boolean testOnBorrow = false;
//
//    /**
//     * 根据address或host:port生成HostAndPort集合
//     * @return
//     */
////    public Set<HostAndPort> buildHostAndPorts() {
////        Set<HostAndPort> hostAndPorts = new HashSet<>();
////        if (StringUtils.isEmpty(address)) {
////            HostAndPort hap = new HostAndPort(host, port);
////            hostAndPorts.add(hap);
////        } else {
////            String[] adds = address.split(",");
////            for (String add : adds) {
////                if (StringUtils.isBlank(add) || StringUtils.isBlank(add = add.trim())) {
////                    continue;
////                }
////                HostAndPort hostAndPort = this.parse(add);
////                if (hostAndPort == null) {
////                    continue;
////                }
////                hostAndPorts.add(hostAndPort);
////            }
////        }
////        return hostAndPorts;
////    }
////
////    public redis.clients.jedis.HostAndPort parse(String hapStr) {
////        String[] hap = hapStr.trim().split(":");
////        if (hap == null || hap.length < 2) {
////            LoggerUtils.fmtError(log, "redis address配置错误.正确的配置格式为host1:port1,host2:port2.配置数据为：" + hapStr);
////            return null;
////        }
////        return new redis.clients.jedis.HostAndPort(hap[0], Integer.parseInt(hap[1]));
////    }
//
//    public String buildHostAndPort(){
//        return host + ":" + port;
//    }
//}