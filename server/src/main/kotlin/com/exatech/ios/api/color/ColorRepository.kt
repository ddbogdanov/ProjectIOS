package com.exatech.ios.api.color

import org.springframework.data.jpa.repository.JpaRepository

interface ColorRepository: JpaRepository<Color, Int> {
    fun deleteByColorId(colorId: Int): Int
}