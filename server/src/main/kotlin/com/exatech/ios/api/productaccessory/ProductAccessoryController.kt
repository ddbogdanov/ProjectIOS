package com.exatech.ios.api.productaccessory

import com.exatech.ios.api.product.Product
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
    @GetMapping("/get-product/{accessoryId}")
    fun getProduct(@PathVariable accessoryId: Int): ResponseEntity<Product> {
        val accessoryOptional = service.findById(accessoryId)
        return if(accessoryOptional.isEmpty) ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        else ResponseEntity.status(HttpStatus.OK).body(accessoryOptional.get().product)
    }

    @PutMapping("/{accessoryId}")
    fun editOne(@PathVariable accessoryId: Int, @RequestParam productId: Int, @RequestBody accessory: ProductAccessory): ResponseEntity<ProductAccessory> {
        if(accessoryId != accessory.productAccessoryId) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        val updatedAccessory = service.update(accessory, productId) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        return ResponseEntity.status(HttpStatus.OK).body(updatedAccessory)
    }

    @PostMapping
    fun saveOne(@RequestBody accessory: ProductAccessory): ResponseEntity<ProductAccessory> {
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