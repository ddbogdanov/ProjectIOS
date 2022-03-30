package com.exatech.ios.api.productorder

import com.exatech.ios.api.product.Product
import javax.persistence.*

@Table(name="product_order")
@Entity
data class ProductOrder
(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_order_id")
    val productOrderId: Int,

    @ManyToOne(fetch=FetchType.LAZY, cascade=[CascadeType.REFRESH])
    @JoinColumn(name="product_id", nullable=false)
    val product: Product,

    @Column(name="color")
    val color: String,
    @Column(name="quantity", nullable=false)
    val quantity: Int,
    @Column(name="completed", columnDefinition="boolean default false", nullable=false)
    val completed: Boolean = false
)
