package com.fussionlabs.ordermanagement.kafka

import com.fussionlabs.ordermanagement.model.Order
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaOrderConsumer {

    @KafkaListener(topics = ["orders"], groupId = "order_group")
    fun consume(order: Order) {
        println("Order received: $order")
    }
}