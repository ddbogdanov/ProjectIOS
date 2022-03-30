package com.exatech.ios.api.productorder

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductOrderController(val service: ProductOrderService) {

    @GetMapping("/order")
    fun getAll(): List<ProductOrder> = service.findAll()
    @GetMapping("/order/{productOrderId}")
    fun getOne(@PathVariable productOrderId: Int): ResponseEntity<ProductOrder> = ResponseEntity.of(service.findById(productOrderId))

    @PostMapping("/order")
    fun saveOne(@RequestBody productOrder: ProductOrder): ResponseEntity<ProductOrder> {
        val savedProduct = service.save(productOrder)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct)
    }
    //TODO PostMapping("/product/{productId}/order")


    @DeleteMapping("order/{productOrderId}")
    fun deleteOne(@PathVariable productOrderId: Int): ResponseEntity<ProductOrder> {
        val responseStatus = service.deleteById(productOrderId)
        return ResponseEntity.status(responseStatus).build()
    }
}