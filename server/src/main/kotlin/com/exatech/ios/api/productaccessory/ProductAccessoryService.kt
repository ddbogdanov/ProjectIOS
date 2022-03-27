package com.exatech.ios.api.productaccessory

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProductAccessoryService(val db: ProductAccessoryRepo) {

    fun findAll(): List<ProductAccessory> = db.findAll()
    fun findOne(productId: Int): Optional<ProductAccessory> = db.findById(productId)

    fun updateOne(productAccessory: ProductAccessory): ProductAccessory? {
        /*
        val response = ServiceResponse<ProductAccessory>()

        return if(!db.existsById(accessoryId)) {
            response.statusCode(HttpStatus.NOT_FOUND)
        }
        else if(productAccessory.productAccessoryId != accessoryId) {
            response.statusCode(HttpStatus.BAD_REQUEST).message("ID doesn't match")
        }
        else {
            response.responseObject(db.save(productAccessory)).statusCode(HttpStatus.OK)
        }
        */
        if(!db.existsById(productAccessory.productAccessoryId)) {
            return null;
        }
        return db.save(productAccessory)
    }

    fun saveOne(productAccessory: ProductAccessory): ProductAccessory {
        return db.save(productAccessory)
    }

    @Transactional
    fun deleteByAccessoryId(accessoryId: Int): HttpStatus {
        val delCount = db.deleteByProductAccessoryId(accessoryId)
        if(delCount < 1) {
            return HttpStatus.NOT_FOUND
        }

        return HttpStatus.NO_CONTENT
    }


    fun findByProductId(productId: Int): List<ProductAccessory> { //TODO: Temporary Function!
        return listOf(ProductAccessory(404, 0, "Hello World!", 0))
    }
}