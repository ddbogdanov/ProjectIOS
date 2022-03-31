package com.exatech.ios.api.color

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ColorService(val db: ColorRepository) {
    fun findAll(): List<Color> = db.findAll()
    fun findById(colorId: Int): Optional<Color> = db.findById(colorId)

    fun save(color: Color): Color {
        return db.save(color)
    }

    @Transactional
    fun deleteById(colorId: Int): HttpStatus {
        val delCount = db.deleteByColorId(colorId)

        if(delCount < 1) {
            return HttpStatus.NOT_FOUND
        }

        return HttpStatus.NO_CONTENT
    }
}