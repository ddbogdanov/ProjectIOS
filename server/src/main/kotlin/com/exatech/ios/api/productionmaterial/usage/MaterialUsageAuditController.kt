package com.exatech.ios.api.productionmaterial.usage

import com.exatech.ios.api.productionmaterial.calculated.CalculatedMaterial
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/material-usage-audit")
class MaterialUsageAuditController(val service: MaterialUsageAuditService) {

    @GetMapping
    fun getAll(): List<MaterialUsageAudit> = service.findAll()
    @GetMapping("/{materialUsageAuditId}")
    fun getOne(@PathVariable materialUsageAuditId: Int): ResponseEntity<MaterialUsageAudit> = ResponseEntity.of(service.findOne(materialUsageAuditId))

}