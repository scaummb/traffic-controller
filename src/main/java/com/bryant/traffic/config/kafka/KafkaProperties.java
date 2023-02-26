package com.bryant.traffic.config.kafka;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "kafka.config")
@ConditionalOnExpression(value = "${kafka.config.enable:true}")
@Data
public class KafkaProperties {

    private String bootstrapServers;

    private String clientId;

}
