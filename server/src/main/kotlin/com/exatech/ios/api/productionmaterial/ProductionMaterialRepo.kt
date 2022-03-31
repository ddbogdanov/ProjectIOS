package com.exatech.ios.api.productionmaterial

import org.springframework.data.jpa.repository.JpaRepository

interface ProductionMaterialRepo: JpaRepository<ProductionMaterial, Int> {
    fun deleteByProductionMaterialId(productionMaterialId: Int): Int
}