package com.exatech.ios.api.productaccessory

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity as ResponseEntity

@RestController
@RequestMapping("/product-accessories")
class ProductAccessoryController(val service: ProductAccessoryService) {
    //TODO Mapping to find the product an accessory belongs to.

    @GetMapping
    fun getAll(): List<ProductAccessory> = service.findAll()
    @GetMapping("/{accessoryId}")
    fun getOne(@PathVariable accessoryId: Int): ResponseEntity<ProductAccessory> = ResponseEntity.of(service.findById(accessoryId))

    @PutMapping("/{accessoryId}")
    fun editOne(@PathVariable accessoryId: Int, @RequestParam productId: Int, @RequestBody accessory: ProductAccessory): ResponseEntity<ProductAccessory> {
        //TODO Check if material type exists by its name/type. Do not add a new material type if it already exists.
        if(accessoryId != accessory.productAccessoryId) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        val updatedAccessory = service.update(accessory, productId) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        return ResponseEntity.status(HttpStatus.OK).body(updatedAccessory)
    }

    @PostMapping
    fun saveOne(@RequestBody accessory: ProductAccessory): ResponseEntity<ProductAccessory> {
        //TODO Check if material type exists by its name/type. Do not add a new material type if it already exists.
        val serviceResponse = service.save(accessory)
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceResponse)
    }

    @DeleteMapping
    fun deleteAll() {
        return service.deleteAll()
    }
    @DeleteMapping("/{accessoryId}")
    fun deleteOne(@PathVariable accessoryId: Int): ResponseEntity<ProductAccessory> {
        val serviceResponse = service.deleteById(accessoryId)
        return ResponseEntity.status(serviceResponse).build()
    }
}