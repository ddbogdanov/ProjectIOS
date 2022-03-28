package com.exatech.ios.api.productaccessory

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity as ResponseEntity

@RestController
@RequestMapping("/product-accessories")
class ProductAccessoryController(val service: ProductAccessoryService) {

    @GetMapping
    fun getAll(): List<ProductAccessory> = service.findAll()
    @GetMapping("/{accessoryId}")
    fun getOne(@PathVariable accessoryId: Int): ResponseEntity<ProductAccessory> = ResponseEntity.of(service.findById(accessoryId))

    @PutMapping
    fun editOne(@RequestBody accessory: ProductAccessory): ResponseEntity<ProductAccessory> {
        val updatedAccessory = service.update(accessory) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        return ResponseEntity.status(HttpStatus.OK).body(updatedAccessory)
    }

    @PostMapping
    fun saveOne(@RequestBody accessory: ProductAccessory): ResponseEntity<ProductAccessory> {
        val serviceResponse = service.save(accessory)
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceResponse)
    }

    /**
     *  TODO: !!!!!!TEMPORARY FUNCTION!!!!!!
     */
    @DeleteMapping
    fun deleteAll() {
        return service.deleteAll()
    }
    @DeleteMapping("/{accessoryId}")
    fun deleteOne(@PathVariable accessoryId: Int): ResponseEntity<ProductAccessory> {
        val serviceResponse = service.deleteById(accessoryId)
        return ResponseEntity.status(serviceResponse).build()
    }



    //TODO: This mapping should exist under products
    @GetMapping("/product/{productId}")
    fun getOneById(@PathVariable productId: Int): List<Any> = service.findByProductId(productId) ?: listOf("Hello!")
}