package com.exatech.ios.api.color

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/color")
class ColorController(val service: ColorService) {
    @GetMapping
    fun getAll(): List<Color> = service.findAll()
    @GetMapping("/{colorId}")
    fun getOne(@PathVariable colorId: Int): ResponseEntity<Color> = ResponseEntity.of(service.findById(colorId))

    @PostMapping
    fun saveOne(@RequestBody color: Color): ResponseEntity<Color> {
        val savedColor = service.save(color)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedColor)
    }


    @DeleteMapping("/{colorId}")
    fun deleteOne(@PathVariable colorId: Int): ResponseEntity<Color> {
        val responseStatus = service.deleteById(colorId)
        return ResponseEntity.status(responseStatus).build()
    }
}