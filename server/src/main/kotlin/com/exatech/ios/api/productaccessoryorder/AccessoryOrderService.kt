package com.exatech.ios.api.productaccessoryorder

import com.exatech.ios.api.productaccessory.ProductAccessoryService
import com.exatech.ios.api.productorder.ProductOrder
import com.exatech.ios.api.productorder.ProductOrderService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AccessoryOrderService(val db: AccessoryOrderRepo, val accs: ProductAccessoryService, val pos: ProductOrderService) {
    fun findAll(): List<AccessoryOrder> = db.findAll()
    fun findById(accessoryOrderId: Int): Optional<AccessoryOrder> = db.findById(accessoryOrderId)
    fun findByCompletion(areCompleted: Boolean): List<AccessoryOrder> = db.findAllByCompleted(areCompleted)

    fun saveByAccessoryId(accessoryId: Int, productOrderId: Int, accessoryOrder: AccessoryOrder): AccessoryOrder? {
        val accessoryOptional = accs.findById(accessoryId)
        if(accessoryOptional.isEmpty) return null

        val productOrderOptional = pos.findById(productOrderId)
        if(productOrderOptional.isEmpty) return null

        accessoryOrder.productAccessory = accessoryOptional.get()
        accessoryOrder.productOrder = productOrderOptional.get()
        return db.save(accessoryOrder)
    }

    fun update(accessoryOrder: AccessoryOrder): AccessoryOrder? {
        val accessoryOrderOptional = db.findById(accessoryOrder.accessoryOrderId)
        if (accessoryOrderOptional.isEmpty) return null

        val productAccessoryOptional = accs.findById(accessoryOrderOptional.get().productAccessory.productAccessoryId)
        return if (productAccessoryOptional.isEmpty) null
        else {
            accessoryOrder.productAccessory = productAccessoryOptional.get()
            db.save(accessoryOrder)
        }
    }
    fun completeOne(accessoryOrderId: Int): HttpStatus {
        val accessoryOrderOptional = db.findById(accessoryOrderId)

        if(accessoryOrderOptional.isEmpty) return HttpStatus.NOT_FOUND
        val accessoryOrder = accessoryOrderOptional.get()
        accessoryOrder.completed = true

        db.save(accessoryOrder)

        return HttpStatus.OK
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