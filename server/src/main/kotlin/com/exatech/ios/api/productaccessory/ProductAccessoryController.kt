package com.exatech.ios.api.productaccessory

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product-accessories")
class ProductAccessoryController(val service: ProductAccessoryService) {

    @GetMapping
    fun getAllProductAccessory(): List<ProductAccessory> = service.findAll()

    //TODO: This mapping should exist under products
    @GetMapping("/product/{productId}")
    fun getProductAccessoryByProductId(@PathVariable productId: Int): List<ProductAccessory> = service.findByProductId(productId)
}