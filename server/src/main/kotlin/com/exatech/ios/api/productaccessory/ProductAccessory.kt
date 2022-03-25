package com.exatech.ios.api.productaccessory

import javax.persistence.*

@Table(name="product_accessory")
@Entity
data class ProductAccessory
(
        @Id val product_accessory_id: Int,
        /*ManyToOne materialtype and product*/
        @Column(name="product_id") val productId: Int,
        @Column(name="name") val name: String,
        @Column(name = "size") val size: Int
)
