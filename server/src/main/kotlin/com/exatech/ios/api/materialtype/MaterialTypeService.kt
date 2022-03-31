package com.exatech.ios.api.materialtype

import com.exatech.ios.api.product.ProductService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MaterialTypeService(val db: MaterialTypeRepo, val ps: ProductService) {

    fun findAll(): List<MaterialType> = db.findAll()
    fun findById(materialTypeId: Int): Optional<MaterialType> = db.findById(materialTypeId)

    fun save(materialType: MaterialType): MaterialType {
        return db.save(materialType)
    }

    @Transactional
    fun deleteById(materialTypeId: Int): HttpStatus {
        val delCount = db.deleteByMaterialTypeId(materialTypeId)

        if(delCount < 1) {
            return HttpStatus.NOT_FOUND
        }

        return HttpStatus.NO_CONTENT
    }
}