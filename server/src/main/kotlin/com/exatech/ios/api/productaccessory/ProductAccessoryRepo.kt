package com.exatech.ios.api.productaccessory

import com.exatech.ios.api.product.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProductAccessoryRepo: JpaRepository<ProductAccessory, Int> {
    //TODO: Temporary function!
    //fun findAllByProductId(productId: Int): List<ProductAccessory>
    fun deleteByProductAccessoryId(accessoryId: Int): Int

//    @Query()
//    fun findProductAccessoryProductByProductAccessoryId(accessoryId: Int): Product
}