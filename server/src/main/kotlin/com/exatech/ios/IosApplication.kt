package com.exatech.ios

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IosApplication

fun main(args: Array<String>) {
    //TODO Create user table and basic validation
    //TODO Report generation
    //TODO Create productorder and accessoryorder audit tables to store date and time when an order is completed. write trigger for this material expenditure should also be stored
    runApplication<IosApplication>(*args)
}
