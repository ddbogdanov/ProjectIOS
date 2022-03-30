package com.exatech.ios.api.materialtype

import org.springframework.data.jpa.repository.JpaRepository

interface MaterialTypeRepo:JpaRepository<MaterialType, Int> {

    fun deleteByMaterialTypeId(materialTypeId: Int): Int
}