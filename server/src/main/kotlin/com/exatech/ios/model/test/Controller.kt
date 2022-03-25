package com.exatech.ios.model.test

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class Controller(val db : TestRepo) {

    @GetMapping("/get")
    fun getTest(): List<Test> = db.findAll();

    @PostMapping("/post")
    fun postTest(t: Test): Test = db.save(t)
}