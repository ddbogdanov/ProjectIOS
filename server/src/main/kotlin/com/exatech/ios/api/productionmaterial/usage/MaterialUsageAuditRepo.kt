package com.exatech.ios.api.productionmaterial.usage

import org.springframework.data.jpa.repository.JpaRepository

interface MaterialUsageAuditRepo: JpaRepository<MaterialUsageAudit, Int> {

}