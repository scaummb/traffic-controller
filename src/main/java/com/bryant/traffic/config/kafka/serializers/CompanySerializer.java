package com.bryant.traffic.config.kafka.serializers;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;
import scala.concurrent.java8.FuturesConvertersImpl.P;

public class CompanySerializer implements Serializer<Company> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, Company data) {
        if (data == null) {
            return null;
        }
        byte[] name, address;
        try {
            if (data.getName() != null) {
                name = data.getName().getBytes(StandardCharsets.UTF_8);
            } else {
                name = new byte[0];
            }

            if (data.getAddress() != null) {
                address = data.getAddress().getBytes(StandardCharsets.UTF_8);
            } else {
                address = new byte[0];
            }

            ByteBuffer buffer = ByteBuffer.allocate(data.toString().length());
            buffer.put(data.toString().getBytes());
            return buffer.array();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new byte[0];
    }
}
