package com.exatech.ios.api.manufacturer

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ManufacturerService(val db: ManufacturerRepo) {

    fun findAll(): List<Manufacturer> = db.findAll()
    fun findOne(manufacturerId: Int): Optional<Manufacturer> = db.findById(manufacturerId)

    fun save(manufacturer: Manufacturer): Manufacturer {
        return db.save(manufacturer)
    }

    @Transactional
    fun deleteById(manufacturerId: Int): HttpStatus {
        val delCount = db.deleteByManufacturerId(manufacturerId)
        if(delCount < 1) {
            return HttpStatus.NOT_FOUND
        }
        return HttpStatus.NO_CONTENT
    }
}