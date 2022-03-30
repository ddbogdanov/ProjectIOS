package com.exatech.ios.api.materialtype

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/material-type")
class MaterialTypeController(val service: MaterialTypeService) {

    @GetMapping
    fun getAll(): List<MaterialType> = service.findAll()
    @GetMapping("/{materialTypeId}")
    fun getOne(@PathVariable materialTypeId: Int): ResponseEntity<MaterialType> = ResponseEntity.of(service.findById(materialTypeId))

    //TODO Add POST mapping

    @DeleteMapping("/{materialTypeId}")
    fun deleteOne(@PathVariable materialTypeId: Int): ResponseEntity<MaterialType> {
        val responseStatus = service.deleteById(materialTypeId)
        return ResponseEntity.status(responseStatus).build()
    }
}