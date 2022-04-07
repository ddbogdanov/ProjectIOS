package com.exatech.ios.api.productionmaterial.usage

import com.exatech.ios.api.productionmaterial.calculated.CalculatedMaterial
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Service
class MaterialUsageAuditService(val db: MaterialUsageAuditRepo) {

    fun findAll(): List<MaterialUsageAudit> = db.findAll()
    fun findOne(materialUsageAuditId: Int): Optional<MaterialUsageAudit> = db.findById(materialUsageAuditId)

    fun findAllWithinLastWeek(): List<MaterialUsageAudit> {
        val weekAgoDate = LocalDateTime.now().minusWeeks(1)
        return db.findAllWithDatePerformedAfter(weekAgoDate)
    }
}