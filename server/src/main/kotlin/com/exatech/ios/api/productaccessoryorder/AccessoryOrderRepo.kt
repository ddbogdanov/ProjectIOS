package com.exatech.ios.api.productaccessoryorder

import org.springframework.data.jpa.repository.JpaRepository

interface AccessoryOrderRepo: JpaRepository<AccessoryOrder, Int> {

    fun findAllByCompleted(areCompleted: Boolean): List<AccessoryOrder>
    fun deleteByAccessoryOrderId(accessoryOrderId: Int): Int
}