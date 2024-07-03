package com.fussionlabs.ordermanagement.config


import com.fussionlabs.ordermanagement.model.Order
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer

/**
 * Kafka configuration class for setting up Kafka producer and consumer factories,
 * Kafka templates, and Kafka listener container factories.
 *
 * This class is annotated with `@Configuration` and `@EnableKafka` to enable Kafka support
 * and to define beans for Kafka producers and consumers.
 */
@Configuration
@EnableKafka
class KafkaConfig {

    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapServers: String

    @Value("\${spring.kafka.consumer.group-id}")
    private lateinit var consumerGroupId: String

    /**
     * Creates a Kafka producer factory bean.
     *
     * This factory configures the necessary properties for the Kafka producer,
     * including bootstrap servers, key serializer, and value serializer.
     *
     * @return A `ProducerFactory` for creating Kafka producers with specified configurations.
     */
    @Bean
    fun producerFactory(): ProducerFactory<String, Order> {
        val configProps = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        )
        return DefaultKafkaProducerFactory(configProps)
    }

    /**
     * Creates a Kafka template bean.
     *
     * This template is used for sending messages to Kafka topics using the producer factory.
     *
     * @return A `KafkaTemplate` for sending messages to Kafka topics.
     */
    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Order> {
        return KafkaTemplate(producerFactory())
    }

    /**
     * Creates a Kafka consumer factory bean.
     *
     * This factory configures the necessary properties for the Kafka consumer,
     * including bootstrap servers, group ID, key deserializer, and value deserializer.
     *
     * @return A `ConsumerFactory` for creating Kafka consumers with specified configurations.
     */
    @Bean
    fun consumerFactory(): ConsumerFactory<String, Order> {
        val configProps = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.GROUP_ID_CONFIG to consumerGroupId,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to ErrorHandlingDeserializer::class.java,
            ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS to JsonDeserializer::class.java.name,
            JsonDeserializer.TRUSTED_PACKAGES to "*",
            JsonDeserializer.VALUE_DEFAULT_TYPE to Order::class.java.name
        )
        return DefaultKafkaConsumerFactory(configProps)
    }

    /**
     * Creates a Kafka listener container factory bean.
     *
     * This factory is used to configure Kafka listener containers to receive messages from Kafka topics.
     *
     * @return A `ConcurrentKafkaListenerContainerFactory` for creating Kafka listener containers.
     */
    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Order> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Order>()
        factory.consumerFactory = consumerFactory()
        return factory
    }
}