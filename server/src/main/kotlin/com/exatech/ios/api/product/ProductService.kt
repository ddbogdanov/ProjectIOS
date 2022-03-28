package com.exatech.ios.api.product

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
class ProductService(val db: ProductRepo) {

    fun findAll(): List<Product> = db.findAll()
    fun findById(productId: Int): Optional<Product> = db.findById(productId)

    fun save(product: Product): Product {
        return db.save(product)
    }

    @Transactional
    fun deleteById(productId: Int): HttpStatus {
        val delCount = db.deleteByProductId(productId)
        if(delCount < 1) {
            return HttpStatus.NOT_FOUND
        }

        return HttpStatus.NO_CONTENT
    }
}