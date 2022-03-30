package com.exatech.ios.api.productorder

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProductOrderService(val db: ProductOrderRepo) {
    fun findAll(): List<ProductOrder> = db.findAll()
    fun findById(productOrderId: Int): Optional<ProductOrder> = db.findById(productOrderId)

    fun save(productOrder: ProductOrder): ProductOrder {
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