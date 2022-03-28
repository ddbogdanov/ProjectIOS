package com.exatech.ios.api.product

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(val service: ProductService) {

    @GetMapping
    fun getAll(): List<Product> = service.findAll()
    @GetMapping("/{productId}")
    fun getOne(@PathVariable productId: Int): ResponseEntity<Product> = ResponseEntity.of(service.findById(productId))

    @PostMapping
    fun saveOne(@RequestBody product: Product): ResponseEntity<Product>? {
        val savedProduct = service.save(product)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct)
    }

    @DeleteMapping("/{productId}")
    fun deleteOne(@PathVariable productId: Int):ResponseEntity<Product> {
        val serviceResponse = service.deleteById(productId)
        return ResponseEntity.status(serviceResponse).build()
    }
}