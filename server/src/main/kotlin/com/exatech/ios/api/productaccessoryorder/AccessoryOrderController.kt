package com.exatech.ios.api.productaccessoryorder

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product-accessory")
class AccessoryOrderController(val service: AccessoryOrderService) {
    @GetMapping("/order")
    fun getAll(): List<AccessoryOrder> = service.findAll()
    @GetMapping("/order/{accessoryOrderId}")
    fun getOne(@PathVariable accessoryOrderId: Int): ResponseEntity<AccessoryOrder> = ResponseEntity.of(service.findById(accessoryOrderId))

    @PostMapping("/order")
    fun saveOneByIdsFromParams(@RequestParam accessoryId: Int, @RequestParam productOrderId: Int, @RequestBody accessoryOrder: AccessoryOrder): ResponseEntity<AccessoryOrder> {
        val savedProductOrder = service.saveByAccessoryId(accessoryId, productOrderId, accessoryOrder)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProductOrder)
    }

    //TODO: Edit
    //TODO: Set Completed

    @DeleteMapping("order/{accessoryOrderId}")
    fun deleteOne(@PathVariable accessoryOrderId: Int): ResponseEntity<AccessoryOrder> {
        val serviceResponse = service.deleteById(accessoryOrderId)
        return ResponseEntity.status(serviceResponse).build()
    }
}