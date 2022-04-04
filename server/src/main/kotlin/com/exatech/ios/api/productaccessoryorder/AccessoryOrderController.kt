package com.exatech.ios.api.productaccessoryorder

import com.exatech.ios.api.productionmaterial.calculated.CalculatedMaterial
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
    @GetMapping("/order/completed")
    fun getByCompletion(@RequestParam areCompleted: Boolean): List<AccessoryOrder> = service.findByCompletion(areCompleted)

    @PutMapping("/order/{accessoryOrderId}")
    fun editOne(@PathVariable accessoryOrderId: Int, @RequestBody accessoryOrder: AccessoryOrder): ResponseEntity<AccessoryOrder> {
        if(accessoryOrderId != accessoryOrder.accessoryOrderId) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        val updatedOrder = service.update(accessoryOrder) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder)
    }
    @PutMapping("/order/complete/{accessoryOrderId}")
    fun completeOne(@PathVariable accessoryOrderId: Int, @RequestParam calcMaterialUsedId: Int): ResponseEntity<AccessoryOrder> {
        val updateStatus = service.completeOne(accessoryOrderId, calcMaterialUsedId)
        return ResponseEntity.status(updateStatus).build()
    }

    @PostMapping("/{accessoryId}/order/{productOrderId}")
    fun saveOneByIdsFromParams(@PathVariable accessoryId: Int, @PathVariable productOrderId: Int, @RequestBody accessoryOrder: AccessoryOrder): ResponseEntity<AccessoryOrder> {
        val savedProductOrder = service.saveByAccessoryId(accessoryId, productOrderId, accessoryOrder)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProductOrder)
    }

    @DeleteMapping("order/{accessoryOrderId}")
    fun deleteOne(@PathVariable accessoryOrderId: Int): ResponseEntity<AccessoryOrder> {
        val serviceResponse = service.deleteById(accessoryOrderId)
        return ResponseEntity.status(serviceResponse).build()
    }
}