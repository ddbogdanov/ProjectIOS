package com.exatech.ios.model.test

import org.springframework.data.jpa.repository.JpaRepository

interface TestRepo : JpaRepository<Test, Int> {

}