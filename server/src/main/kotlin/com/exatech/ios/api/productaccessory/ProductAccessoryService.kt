package com.exatech.ios.api.productaccessory

import com.exatech.ios.api.product.ProductService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProductAccessoryService(val db: ProductAccessoryRepo, val ps: ProductService) {

    fun findAll(): List<ProductAccessory> = db.findAll()
    fun findById(productId: Int): Optional<ProductAccessory> = db.findById(productId)

    fun update(productAccessory: ProductAccessory, productId: Int): ProductAccessory? {
        if(!db.existsById(productAccessory.productAccessoryId) || !ps.existsById(productId)) {
            return null
        }
        productAccessory.product = ps.findById(productId).get()
        return db.save(productAccessory)
    }
    fun save(productAccessory: ProductAccessory): ProductAccessory {
        return db.save(productAccessory)
    }

    @Transactional
    fun deleteAll() {
        return db.deleteAll()
    }

    @Transactional
    fun deleteById(accessoryId: Int): HttpStatus {
        val delCount = db.deleteByProductAccessoryId(accessoryId)
        if(delCount < 1) {
            return HttpStatus.NOT_FOUND
        }

        return HttpStatus.NO_CONTENT
    }


    fun findByProductId(productId: Int): List<ProductAccessory>? { //TODO: Temporary Function!
        return null//listOf(ProductAccessory(404, null, "Hello World!", 0))
    }
}