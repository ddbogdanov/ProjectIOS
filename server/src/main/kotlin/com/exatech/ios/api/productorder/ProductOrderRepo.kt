package com.exatech.ios.api.productorder

import org.springframework.data.jpa.repository.JpaRepository

interface ProductOrderRepo: JpaRepository<ProductOrder, Int> {

    fun findAllByCompleted(completed: Boolean): List<ProductOrder>
    fun deleteByProductOrderId(productOrderId: Int): Int
}