package com.fussionlabs.ordermanagement.kafka

import com.fussionlabs.ordermanagement.model.Order
import com.fussionlabs.ordermanagement.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaOrderConsumer @Autowired constructor(
    private val orderService: OrderService
) {

    @KafkaListener(topics = ["\${spring.kafka.topic}"], groupId = "\${spring.kafka.consumer.group-id}")
    fun consume(order: Order) {
        println("Order received: $order")
        orderService.createOrder(order)
    }
}