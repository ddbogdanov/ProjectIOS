package com.exatech.ios.api.productionmaterial

import com.exatech.ios.api.productaccessoryorder.AccessoryOrder
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

    //@PostMapping("/type/{typeId}/color/{colorId}/manufacturer/{manufacturerId}/unit/{unit}")
    @PostMapping
    fun saveOne(@RequestBody productionMaterial: ProductionMaterial): ResponseEntity<ProductionMaterial> {
        val savedProductionMaterial = service.save(productionMaterial)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProductionMaterial)
    }
}