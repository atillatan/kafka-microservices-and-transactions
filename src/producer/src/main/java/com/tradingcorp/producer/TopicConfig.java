package com.tradingcorp.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

/*   Atilla Tanrikulu

	 Check Kafka Configuration (config/server.properties)
	 delete.topic.enable: true (default: false)
	 By default deleting Topic not allowed, it’s a reasonable in production
	 environment
	 auto.create.topics.enable: true (if producer start sending message with non
	 existing Topic kafka creates Topic automatically)
	 I created 2 partition because, I create consumer group with 2 members
	 (purpose is POC)
 */
/**
 * @author Atilla Tanrikulu
 * 
 */
@Configuration
public class TopicConfig {
	
	@Value(value = "${spring.kafka.producer.bootstrap-servers}")
	private String bootstrapAddress;

	@Value(value = "${trade.topic.partition.count}")
	private int tradeTopicPartitionCount;

	@Value(value = "${trade.topic.producer.target.partition.hashkey}")
	private String producerTargetPartitionHashkey;

	@Value(value = "${trade.topic.name}")
	private String tradeTopicName;

	@Bean
	public NewTopic tradeTopic() {
		return TopicBuilder.name(tradeTopicName).partitions(tradeTopicPartitionCount).replicas(1).build();
	}

// 	 Spring boot, automatically creates Kafka topics for us.
//	 We don't need to create KafkaAdmin,TopicBuilder

    @Bean
    public KafkaAdmin kafkaAdmin() 
    {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }
}
