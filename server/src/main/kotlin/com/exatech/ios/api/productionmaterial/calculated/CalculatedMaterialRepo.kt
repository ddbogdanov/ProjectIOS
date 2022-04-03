package com.exatech.ios.api.productionmaterial.calculated

import com.exatech.ios.api.color.Color
import com.exatech.ios.api.materialtype.MaterialType
import org.springframework.data.jpa.repository.JpaRepository

interface CalculatedMaterialRepo: JpaRepository<CalculatedMaterial, Int> {
    fun findByMaterialTypeAndColor(matType: MaterialType, color: Color): List<CalculatedMaterial>

}