package com.exatech.ios.api.productorder

import com.exatech.ios.api.product.ProductService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class ProductOrderService(val db: ProductOrderRepo, val ps: ProductService) {
    fun findAll(): List<ProductOrder> = db.findAll()
    fun findById(productOrderId: Int): Optional<ProductOrder> = db.findById(productOrderId)
    fun findByCompletion(areCompleted: Boolean): List<ProductOrder> = db.findAllByCompleted(areCompleted)

    fun save(productOrder: ProductOrder): ProductOrder {
        return db.save(productOrder)
    }

    fun update(productOrder: ProductOrder): ProductOrder? {
        val productOrderOptional = db.findById(productOrder.productOrderId)
        if(productOrderOptional.isEmpty) return null

        val productOptional = ps.findById(productOrderOptional.get().product.productId)
        return if(productOptional.isEmpty) null
        else {
            productOrder.product = productOptional.get()
            db.save(productOrder)
        }
    }
    fun completeOne(productOrderId: Int): HttpStatus {
        val productOrderOptional = db.findById(productOrderId)
        if(productOrderOptional.isEmpty) return HttpStatus.NOT_FOUND

        val productOrder = productOrderOptional.get()
        productOrder.completed = true
        productOrder.dateCompleted = LocalDateTime.now()
        db.save(productOrder)

        return HttpStatus.OK
    }

    fun saveByProductId(productId: Int, productOrder: ProductOrder): ProductOrder? {
        val productOptional = ps.findById(productId)
        if(productOptional.isEmpty) return null

        productOrder.product = productOptional.get()
        return db.save(productOrder)
    }

    @Transactional
    fun deleteById(productOrderId: Int): HttpStatus {
        val delCount = db.deleteByProductOrderId(productOrderId)
        if(delCount < 1) {
            return HttpStatus.NOT_FOUND
        }

        return HttpStatus.NO_CONTENT
    }
}