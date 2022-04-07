package com.exatech.ios.api.report

import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.ByteArrayInputStream

@RestController
@RequestMapping("/report")
class ReportController(val service: ReportService) {

    /**
     * Returns HTTP Status NOT_FOUND if there are no calculatedMaterial max or min
     * Returns HTTP Status Internal Server Error if something goes wrong with file handling or other stuff
     */
    @GetMapping("/calculated-material")
    fun getMaterialReport(): ResponseEntity<Any?> {

        val bis: ByteArrayInputStream = service.generateMaterialReport()
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        val headers = HttpHeaders()
        headers.add("Content-Disposition", "inline; filename=materialreport.pdf")

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_PDF)
            .body(InputStreamResource(bis))
    }

    @GetMapping("/material-usage")
    fun getMaterialUsageReport(): ResponseEntity<Any?> {
        val bis: ByteArrayInputStream = service.generateMaterialUsageReport()
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        val headers = HttpHeaders()
        headers.add("Content-Disposition", "inline; filename=materialusagereport.pdf")

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_PDF)
            .body(InputStreamResource(bis))
    }

}