package com.exatech.ios.api.product

import com.exatech.ios.api.productaccessory.ProductAccessory
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

    @GetMapping("/{productId}/accessories")
    fun getAccessories(@PathVariable productId: Int): ResponseEntity<Any>{
        val productOptional = service.findById(productId)

        return if(productOptional.isPresent) {
            ResponseEntity.status(HttpStatus.OK).body(productOptional.get().productAccessories)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @PutMapping("/{productId}")
    fun editOne(@PathVariable productId: Int, @RequestBody product: Product): ResponseEntity<Product> {
        if(productId != product.productId) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        val updatedAccessory = service.update(product) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        return ResponseEntity.status(HttpStatus.OK).body(updatedAccessory)
    }

    @PostMapping
    fun saveOne(@RequestBody product: Product): ResponseEntity<Product> {
        val savedProduct = service.save(product)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct)
    }

    @DeleteMapping("/{productId}")
    fun deleteOne(@PathVariable productId: Int): ResponseEntity<Product> {
        val responseStatus = service.deleteById(productId)
        return ResponseEntity.status(responseStatus).build()
    }
}