package com.bryant.traffic.config.kafka;

import com.bryant.traffic.config.kafka.interceptor.ProducerInterceptorPrefix;
import com.bryant.traffic.config.kafka.partitioner.DemoPartitioner;
import com.bryant.traffic.config.kafka.serializers.Company;
import com.bryant.traffic.config.kafka.serializers.CompanySerializer;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.protocol.types.Field.Str;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerConfig {

    @Autowired
    private KafkaProperties kafkaPropertiesConfig;

    public static Properties config() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // 自定义序列化器
//        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, CompanySerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CompanySerializer.class.getName());

        // 自定义分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DemoPartitioner.class.getName());

        // 自定义生产者拦截器（可以指定多个拦截器，形成拦截器链，","分隔开）
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, ProducerInterceptorPrefix.class.getName());

        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "producer.client.id.demo");
        properties.put(ProducerConfig.RETRIES_CONFIG, "1");
        return properties;

    }

    public static void main(String[] args) {
        Properties properties = config();
        KafkaProducer<String, Company> producer = new KafkaProducer<String, Company>(properties);
        ProducerRecord<String, String> record = new ProducerRecord<>("topic-demo", "hello kafka");

        ProducerRecord<String, Company> record2 = new ProducerRecord<String, Company>("topic-demo", new Company("name", "address"));
        try {
            // 1.发后即完成
//            sendv1(record2, record);
            // 2.同步发送
//            sendv2(producer, record);
            // 3.异步发送
            sendv3(producer, record2);
            Thread.sleep(100000000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void sendv3(
            KafkaProducer<String, Company> producer,
            ProducerRecord<String, Company> record) {
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
                // metadata 和 exception 互斥
                if (!Objects.isNull(exception)) {
                    System.out.println(MessageFormat.format("ex = {0}", exception.getMessage()));
                } else {
                    System.out.println(
                            MessageFormat.format(
                                    "message has sended, topic = {0}, topicPartition = {1},  offset = {2}",
                                    recordMetadata.topic(),
                                    recordMetadata.partition()));
                }
            }
        });
    }

    private static void sendv2(
            KafkaProducer<String, String> producer,
            ProducerRecord<String, String> record) {
        Future<RecordMetadata> future = producer.send(record);
        try {
            RecordMetadata recordMetadata = future.get();
            System.out.println(MessageFormat.format(
                    "topic = {0}, topicPartition = {1},  offset = {2}",
                    recordMetadata.topic(),
                    recordMetadata.partition(),
                    recordMetadata.offset()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            future.cancel(Boolean.TRUE);
        }
    }

    private static <String, Company> void sendv1(
            KafkaProducer<String, Company> producer,
            ProducerRecord<String, Company> record) {
        producer.send(record);
    }
}
