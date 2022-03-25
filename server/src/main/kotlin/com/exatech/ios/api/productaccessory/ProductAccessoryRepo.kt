package com.exatech.ios.api.productaccessory

import org.springframework.data.jpa.repository.JpaRepository

interface ProductAccessoryRepo: JpaRepository<ProductAccessory, Int> {
    //TODO: Temporary function!
    fun findAllByProductId(productId: Int): List<ProductAccessory>
}