package com.exatech.ios.api.productionmaterial.calculated

import com.exatech.ios.api.color.ColorService
import com.exatech.ios.api.materialtype.MaterialTypeService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CalculatedMaterialService(val db: CalculatedMaterialRepo, val mts: MaterialTypeService, val cs: ColorService) {

    fun findAll(): List<CalculatedMaterial> = db.findAll()
    fun findOne(calculatedMaterialId: Int): Optional<CalculatedMaterial> = db.findById(calculatedMaterialId)

    fun findByTypeAndColor(materialTypeId: Int, colorId: Int): List<CalculatedMaterial>? {
        val matType = mts.findById(materialTypeId)
        val color = cs.findById(colorId)

        if(matType.isEmpty || color.isEmpty) return null

        return db.findByMaterialTypeAndColor(matType.get(), color.get())
    }
}