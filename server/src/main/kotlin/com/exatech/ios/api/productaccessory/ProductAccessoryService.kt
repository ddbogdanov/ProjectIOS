package com.exatech.ios.api.productaccessory

import org.springframework.stereotype.Service

@Service
class ProductAccessoryService(val db: ProductAccessoryRepo) {

    fun findAll(): List<ProductAccessory> = db.findAll()
    fun findByProductId(productId: Int): List<ProductAccessory> {
        return listOf(ProductAccessory(404, 0, "Hello World!", 0))
    }

    fun post(productAccessory: ProductAccessory) {
        db.save(productAccessory)
    }
}