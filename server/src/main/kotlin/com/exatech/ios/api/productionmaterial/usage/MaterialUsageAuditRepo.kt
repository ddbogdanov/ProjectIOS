package com.exatech.ios.api.productionmaterial.usage

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate
import java.time.LocalDateTime

interface MaterialUsageAuditRepo: JpaRepository<MaterialUsageAudit, Int> {

    @Query("select m from MaterialUsageAudit m where datePerformed >= :weekAgoDate")
    fun findAllWithDatePerformedAfter(weekAgoDate: LocalDateTime): List<MaterialUsageAudit>
}