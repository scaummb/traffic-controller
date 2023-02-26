package com.bryant.traffic.config.kafka.interceptor;

import java.text.MessageFormat;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * 生产者拦截器
 */
public class ProducerInterceptorPrefix implements ProducerInterceptor<String, String> {

    private volatile long sendSuccess = 0;
    private volatile long sendFailure = 0;
    private static final String TOPIC_PREFIX = "producer-";

    /**
     * 时机：序列化器和分区器之前执行
     * @param record
     * @return
     */
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        String modifyValue = TOPIC_PREFIX + record.topic();
        return record;
    }

    /**
     * 时机：回调callback之前执行
     * @param metadata
     * @param exception
     */
    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (null == exception) {
            sendSuccess++;
        } else {
            sendFailure++;
        }
    }

    @Override
    public void close() {
        double successRatio = (double) sendSuccess / (sendSuccess + sendFailure);
        System.out.println(MessageFormat.format("successRatio = {0}", successRatio));
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
