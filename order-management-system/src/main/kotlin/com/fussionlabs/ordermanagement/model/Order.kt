package com.fussionlabs.ordermanagement.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val productName: String,
    val quantity: Int,
    val price: Double,
    val status: String = "CREATED"
)