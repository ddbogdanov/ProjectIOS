package com.exatech.ios.api.productionmaterial.calculated

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/production-material/calculated")
class CalculatedMaterialController(val service: CalculatedMaterialService) {

    @GetMapping
    fun getAll(): List<CalculatedMaterial> = service.findAll()
    @GetMapping("/{calculatedMaterialId}")
    fun getOne(@PathVariable calculatedMaterialId: Int): ResponseEntity<CalculatedMaterial> = ResponseEntity.of(service.findOne(calculatedMaterialId))

    @GetMapping("/{materialTypeId}/{colorId}")
    fun getAllByTypeAndColor(@PathVariable materialTypeId: Int, @PathVariable colorId: Int): ResponseEntity<List<CalculatedMaterial>> {
        val calculatedMatList =
            service.findByTypeAndColor(materialTypeId, colorId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        if(calculatedMatList.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        return ResponseEntity.status(HttpStatus.OK).body(calculatedMatList)
    }

}