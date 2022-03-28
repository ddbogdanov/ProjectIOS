package com.exatech.ios.api.product

import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepo: JpaRepository<Product, Int> {

    fun deleteByProductId(productId: Int): Int
}