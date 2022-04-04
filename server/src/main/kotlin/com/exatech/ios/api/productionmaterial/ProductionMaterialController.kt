package com.exatech.ios.api.productionmaterial

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/production-material")
class ProductionMaterialController(val service: ProductionMaterialService) {
    @GetMapping()
    fun getAll(): List<ProductionMaterial> = service.findAll()
    @GetMapping("/{productionMaterialId}")
    fun getOne(@PathVariable productionMaterialId: Int): ResponseEntity<ProductionMaterial> = ResponseEntity.of(service.findById(productionMaterialId))

    @PutMapping("/{productionMaterialId}")
    fun editOne(@PathVariable productionMaterialId: Int, @RequestBody productionMaterial: ProductionMaterial): ResponseEntity<ProductionMaterial> {
        if(productionMaterialId != productionMaterial.productionMaterialId) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        val updatedMaterial = service.update(productionMaterial) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        return ResponseEntity.status(HttpStatus.OK).body(updatedMaterial)
    }

    //@PostMapping("/type/{typeId}/color/{colorId}/manufacturer/{manufacturerId}/unit/{unit}")
    @PostMapping
    fun saveOne(@RequestBody productionMaterial: ProductionMaterial): ResponseEntity<ProductionMaterial> {
        val savedProductionMaterial = service.save(productionMaterial)
            ?: return ResponseEntity.status(HttpStatus.CONFLICT).build()

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProductionMaterial)
    }

    @DeleteMapping("/{productionMaterialId}")
    fun deleteOne(@PathVariable productionMaterialId: Int): ResponseEntity<ProductionMaterial> {
        val serviceResponse = service.deleteById(productionMaterialId)
        return ResponseEntity.status(serviceResponse).build()
    }
}