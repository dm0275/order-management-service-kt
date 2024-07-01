package com.fussionlabs.ordermanagement.service

import com.fussionlabs.ordermanagement.model.Order
import com.fussionlabs.ordermanagement.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderService @Autowired constructor(
    private val orderRepository: OrderRepository
) {

    fun createOrder(order: Order): Order {
        return orderRepository.save(order.copy(status = "CREATED"))
    }

    fun updateOrder(order: Order): Order {
        return orderRepository.save(order)
    }

    fun getAllOrders(): List<Order> {
        return orderRepository.findAll()
    }

    fun getOrderById(id: Long): Order? {
        return orderRepository.findById(id).orElse(null)
    }
}