package com.exatech.ios.api.manufacturer

import org.springframework.data.jpa.repository.JpaRepository

interface ManufacturerRepo: JpaRepository<Manufacturer, Int> {
    fun deleteByManufacturerId(manufacturerId: Int): Int
}