package com.fussionlabs.ordermanagement.repository

import com.fussionlabs.ordermanagement.model.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface OrderRepository : JpaRepository<Order, Long>
