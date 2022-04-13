package com.exatech.ios.api.productorder

import com.exatech.ios.api.color.Color
import com.exatech.ios.api.product.Product
import com.exatech.ios.api.productaccessory.ProductAccessory
import com.exatech.ios.api.productaccessoryorder.AccessoryOrder
import com.exatech.ios.api.productionmaterial.calculated.CalculatedMaterial
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Table(name="product_order")
@Entity
data class ProductOrder
(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_order_id")
    val productOrderId: Int,


    @OneToMany(cascade=[CascadeType.ALL], fetch=FetchType.LAZY, mappedBy="productOrder")
    @JsonManagedReference
    val accessoryOrders: List<AccessoryOrder>,

    @ManyToOne(fetch=FetchType.LAZY, cascade=[CascadeType.REFRESH])
    @JoinColumn(name="product_id", nullable=false)
    var product: Product,

    @ManyToOne(fetch=FetchType.LAZY, cascade=[CascadeType.REFRESH])
    @JoinColumn(name="color_id")
    val color: Color,

    @ManyToOne(fetch=FetchType.LAZY, cascade=[CascadeType.REFRESH]) //Specifies the material used to complete the order
    @JoinColumn(name="p_m_calc_id")
    @JsonIgnoreProperties("color", "materialType", "manufacturer", "amount", "name")
    var productionCalcMaterialUsed: CalculatedMaterial,

    @Column(name="quantity", nullable=false)
    val quantity: Int,
    @Column(name="completed", columnDefinition="boolean default false", nullable=false)
    var completed: Boolean = false,
    @Column(name="comments")
    var comments: String,
    @CreationTimestamp
    @Column(name="date_created", nullable=false, updatable=false)
    val dateCreated: LocalDateTime,
    @Column(name="date_completed")
    var dateCompleted: LocalDateTime? = null
)
{
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
