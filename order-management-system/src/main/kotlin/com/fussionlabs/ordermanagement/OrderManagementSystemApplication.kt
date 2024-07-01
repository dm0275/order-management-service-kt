package com.fussionlabs.ordermanagement

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OrderManagementSystemApplication

fun main(args: Array<String>) {
    runApplication<OrderManagementSystemApplication>(*args)
}
