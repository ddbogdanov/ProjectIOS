package com.exatech.ios.api.report

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/report")
class ReportController(val service: ReportService) {

    @GetMapping("/calculated-material")
    fun getMaterialReport(@RequestParam inString: String): ResponseEntity<ByteArray> {

        service.generateMaterialReport(inString)

        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build()
    }
}