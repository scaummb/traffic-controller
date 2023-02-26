package com.bryant.traffic.config.kafka.partitioner;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

/**
 * 分区器
 */
public class DemoPartitioner implements Partitioner {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public int partition(String topic, Object key, byte[] keyBytes,
            Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numberPartitions = partitions.size();
        if (null == keyBytes) {
            // 默认分区器在key为null，并不会选择可用分区
            return counter.getAndIncrement() % numberPartitions;
        } else {
            // 使用MurmurHash2算法，具备高运算性能和低碰撞率
            return Utils.toPositive(Utils.murmur2(keyBytes)) % numberPartitions;
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
