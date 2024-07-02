package com.fussionlabs.ordermanagement.controller

import com.fussionlabs.ordermanagement.kafka.KafkaOrderProducer
import com.fussionlabs.ordermanagement.model.Order
import com.fussionlabs.ordermanagement.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController @Autowired constructor(
    private val orderService: OrderService,
    private val kafkaOrderProducer: KafkaOrderProducer
) {

    @PostMapping
    fun createOrder(@RequestBody order: Order): Order {
        val createdOrder = orderService.createOrder(order)
        kafkaOrderProducer.sendOrder(createdOrder)
        return createdOrder
    }

    @GetMapping
    fun getAllOrders(): List<Order> {
        return orderService.getAllOrders()
    }

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: Long): ResponseEntity<Order> {
        val order = orderService.getOrderById(id)
        return if (order != null) {
            ResponseEntity.ok(order)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}")
    fun updateOrder(@PathVariable id: Long, @RequestBody order: Order): ResponseEntity<Order> {
        return if (orderService.getOrderById(id) != null) {
            val updatedOrder = orderService.updateOrder(order.copy(id = id))
            kafkaOrderProducer.sendOrder(updatedOrder)
            ResponseEntity.ok(updatedOrder)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}