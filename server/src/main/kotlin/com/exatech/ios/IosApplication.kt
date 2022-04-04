package com.exatech.ios

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IosApplication

fun main(args: Array<String>) {
    //TODO Add 'comment' field to productOrder
    //TODO Create user table and basic validation
    runApplication<IosApplication>(*args)
}
