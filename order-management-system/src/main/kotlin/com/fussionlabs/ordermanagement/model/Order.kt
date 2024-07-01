package com.fussionlabs.ordermanagement.model

import jakarta.persistence.*

@Entity
@Table(name = "orders")
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val productName: String,
    val quantity: Int,
    val price: Double,
    val status: String = "CREATED"
)