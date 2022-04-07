package com.exatech.ios.api.productaccessory

import com.exatech.ios.api.product.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProductAccessoryRepo: JpaRepository<ProductAccessory, Int> {
    fun deleteByProductAccessoryId(accessoryId: Int): Int
}