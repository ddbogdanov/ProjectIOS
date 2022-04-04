package com.exatech.ios.api.manufacturer

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/manufacturer")
class ManufacturerController(val service: ManufacturerService) {

    @GetMapping
    fun getAll(): List<Manufacturer> = service.findAll()
    @GetMapping("/{manufacturerId}")
    fun getOne(@PathVariable manufacturerId: Int): ResponseEntity<Manufacturer> = ResponseEntity.of(service.findOne(manufacturerId))

    @PostMapping
    fun saveOne(@RequestBody manufacturer: Manufacturer): ResponseEntity<Manufacturer> {
        val savedManufacturer = service.save(manufacturer)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedManufacturer)
    }

    @DeleteMapping("/{manufacturerId}")
    fun deleteOne(@PathVariable manufacturerId: Int): ResponseEntity<Manufacturer> {
        val responseStatus = service.deleteById(manufacturerId)
        return ResponseEntity.status(responseStatus).build()
    }
}