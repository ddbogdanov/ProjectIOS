//package com.exatech.ios.util
//
//import org.springframework.http.HttpStatus
//
//data class ServiceResponse<T>(var responseObject: T?, var statusCode: HttpStatus, var message: String?) {
//
//    fun responseObject(responseObject: T): ServiceResponse<T> {
//        this.responseObject = responseObject
//        return this
//    }
//    fun statusCode(statusCode: HttpStatus): ServiceResponse<T> {
//        this.statusCode = statusCode
//        return this
//    }
//    fun message(message: String): ServiceResponse<T> {
//        this.message = message
//        return this
//    }
//}
