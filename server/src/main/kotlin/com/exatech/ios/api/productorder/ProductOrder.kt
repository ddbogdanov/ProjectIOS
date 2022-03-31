package com.exatech.ios.api.productorder

import com.exatech.ios.api.color.Color
import com.exatech.ios.api.product.Product
import org.hibernate.Hibernate
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
    var product: Product,

    @ManyToOne(fetch=FetchType.LAZY, cascade=[CascadeType.REFRESH])
    @JoinColumn(name="color_id")
    val color: Color,

    @Column(name="quantity", nullable=false)
    val quantity: Int,
    @Column(name="completed", columnDefinition="boolean default false", nullable=false)
    var completed: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as ProductOrder

        return productOrderId != null && productOrderId == other.productOrderId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(productOrderId = $productOrderId )"
    }
}
