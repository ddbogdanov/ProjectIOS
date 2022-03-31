package com.exatech.ios.api.productorder

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductOrderController(val service: ProductOrderService) {

    //TODO: Mapping to get all not completed, and all completed

    @GetMapping("/order")
    fun getAll(): List<ProductOrder> = service.findAll()
    @GetMapping("/order/{productOrderId}")
    fun getOne(@PathVariable productOrderId: Int): ResponseEntity<ProductOrder> = ResponseEntity.of(service.findById(productOrderId))

    @PutMapping("/order/{productOrderId}")
    fun editOne(@PathVariable productOrderId: Int, @RequestBody productOrder: ProductOrder): ResponseEntity<ProductOrder> {
        if(productOrderId != productOrder.productOrderId) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        val updatedOrder = service.update(productOrder) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder)
    }
    @PutMapping("/order/complete/{productOrderId}")
    fun completeOne(@PathVariable productOrderId: Int): ResponseEntity<ProductOrder> {
        val updateStatus = service.completeOne(productOrderId)
        return ResponseEntity.status(updateStatus).build()
    }

    /**
     * Redundant POST Mappings for now - not sure which we'll actually need to use for now
     * Probably the one allowing for query parameters
     */
    @PostMapping("/order")
    fun saveOne(@RequestBody productOrder: ProductOrder): ResponseEntity<ProductOrder> {
        val savedProductOrder = service.save(productOrder)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProductOrder)
    }
    @PostMapping("/{productId}/order")
    fun saveOneByProductId(@PathVariable productId: Int, @RequestBody productOrder: ProductOrder): ResponseEntity<ProductOrder> {
        val savedProductOrder = service.saveByProductId(productId, productOrder)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProductOrder)
    }
    @PostMapping("/order/PARAM")
    fun saveOneByProductIdFromParam(@RequestParam productId: Int, @RequestBody productOrder: ProductOrder): ResponseEntity<ProductOrder> {
        val savedProductOrder = service.saveByProductId(productId, productOrder)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProductOrder)
    }

    @DeleteMapping("/order/{productOrderId}")
    fun deleteOne(@PathVariable productOrderId: Int): ResponseEntity<ProductOrder> {
        val responseStatus = service.deleteById(productOrderId)
        return ResponseEntity.status(responseStatus).build()
    }
}