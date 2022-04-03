package com.exatech.ios.api.productionmaterial

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProductionMaterialService(val db: ProductionMaterialRepo) {
    fun findAll(): List<ProductionMaterial> = db.findAll()
    fun findById(productionMaterialId: Int): Optional<ProductionMaterial> = db.findById(productionMaterialId)

    fun save(productionMaterial: ProductionMaterial): ProductionMaterial? {
        //TODO Exists by name and manufacturer do NOT save, add amounts instead.
        return if(db.existsById(productionMaterial.productionMaterialId)) null
        else db.save(productionMaterial)
    }

    fun update(productionMaterial: ProductionMaterial): ProductionMaterial? {
        if(!db.existsById(productionMaterial.productionMaterialId)) return null
        return db.save(productionMaterial)
    }

    @Transactional
    fun deleteById(productionMaterialId: Int): HttpStatus {
        val delCount = db.deleteByProductionMaterialId(productionMaterialId)
        if(delCount < 1) {
            return HttpStatus.NOT_FOUND
        }
        return HttpStatus.NO_CONTENT
    }

}