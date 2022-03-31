package com.exatech.ios.api.productaccessoryorder

import com.exatech.ios.api.productaccessory.ProductAccessoryService
import com.exatech.ios.api.productorder.ProductOrderService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AccessoryOrderService(val db: AccessoryOrderRepo, val accs: ProductAccessoryService, val pos: ProductOrderService) {
    fun findAll(): List<AccessoryOrder> = db.findAll()
    fun findById(accessoryOrderId: Int): Optional<AccessoryOrder> = db.findById(accessoryOrderId)

    fun saveByAccessoryId(accessoryId: Int, productOrderId: Int, accessoryOrder: AccessoryOrder): AccessoryOrder? {
        val accessoryOptional = accs.findById(accessoryId)
        if(accessoryOptional.isEmpty) return null

        val productOrderOptional = pos.findById(productOrderId)
        if(productOrderOptional.isEmpty) return null

        accessoryOrder.productAccessory = accessoryOptional.get()
        accessoryOrder.productOrder = productOrderOptional.get()
        return db.save(accessoryOrder)
    }

    @Transactional
    fun deleteById(accessoryOrderId: Int): HttpStatus {
        val delCount = db.deleteByAccessoryOrderId(accessoryOrderId)
        if(delCount < 1) {
            return HttpStatus.NOT_FOUND
        }

        return HttpStatus.NO_CONTENT
    }

}