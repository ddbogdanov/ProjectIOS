package com.exatech.ios.api.productaccessoryorder

import com.exatech.ios.api.color.Color
import com.exatech.ios.api.productaccessory.ProductAccessory
import com.exatech.ios.api.productionmaterial.calculated.CalculatedMaterial
import com.exatech.ios.api.productorder.ProductOrder
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Table(name="product_accessory_order")
@Entity
data class AccessoryOrder
(
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="product_accessory_order_id")
    val accessoryOrderId: Int,

    @ManyToOne(fetch=FetchType.LAZY, cascade=[CascadeType.REFRESH])
    @JoinColumn(name="product_accessory_id", nullable=false)
    var productAccessory: ProductAccessory,

    @ManyToOne(fetch=FetchType.LAZY, cascade=[CascadeType.REFRESH])
    @JoinColumn(name="product_order_id", nullable=false)
    @JsonIgnoreProperties("product", "color", "quantity", "completed", "dateCreated", "dateCompleted")
    @JsonBackReference
    var productOrder: ProductOrder,

    @ManyToOne(fetch=FetchType.LAZY, cascade=[CascadeType.REFRESH])
    @JoinColumn(name="color_id")
    val color: Color,

    @ManyToOne(fetch=FetchType.LAZY, cascade=[CascadeType.REFRESH]) //Specifies the material used to complete the order
    @JoinColumn(name="p_m_calc_id")
    @JsonIgnoreProperties("color", "materialType", "manufacturer", "amount", "name")
    var productionCalcMaterialUsed: CalculatedMaterial?,

    @Column(name="quantity")
    val quantity: Int,
    @Column(name="completed", columnDefinition="boolean default false", nullable=false)
    var completed: Boolean = false,
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
        other as AccessoryOrder

        return accessoryOrderId != null && accessoryOrderId == other.accessoryOrderId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(accessoryOrderId = $accessoryOrderId )"
    }
}
