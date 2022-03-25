package com.exatech.ios.model.test

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "test")
@Entity
data class Test(@Id val id: Int, @Column(name = "s") var s: String?="")