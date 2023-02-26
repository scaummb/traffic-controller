//package com.bryant.traffic.config.cache;
//
//import com.bryant.traffic.config.cache.client.RedisClient;
//import com.bryant.traffic.config.cache.client.SingleRedisClient;
//import java.util.Set;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.Jedis;
//
////@Configuration
////@ConditionalOnClass(Jedis.class)
////@EnableConfigurationProperties(RedisProperties.class)
////@ConditionalOnProperty(name = "redis.auto.config", havingValue = "true")
//public class RedisAutoConfiguration {
//
////    @Bean
////    RedisClient redisClient(RedisProperties properties) {
////        return this.getRedisClient(properties);
////    }
//
//    private RedisClient getRedisClient(BaseRedisProperties properties) {
//        Set<HostAndPort> hostAndPorts = properties.buildHostAndPorts();
//        HostAndPort hostAndPort = hostAndPorts.stream().findFirst().get();
//        properties.setHost(hostAndPort.getHost());
//        properties.setPort(hostAndPort.getPort());
//        return new SingleRedisClient(properties);
//    }
//}
