package com.fussionlabs.ordermanagement.kafka

import com.fussionlabs.ordermanagement.model.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaOrderProducer @Autowired constructor(
    private val kafkaTemplate: KafkaTemplate<String, Order>
) {

    fun sendOrder(order: Order) {
        kafkaTemplate.send("orders", order)
    }
}