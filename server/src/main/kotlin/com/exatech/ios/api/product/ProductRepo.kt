package com.exatech.ios.api.product

import com.exatech.ios.api.materialtype.MaterialType
import com.exatech.ios.api.productaccessory.ProductAccessory
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepo: JpaRepository<Product, Int> {

    fun deleteByProductId(productId: Int): Int
}