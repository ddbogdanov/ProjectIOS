package com.exatech.ios.api.product

import com.exatech.ios.api.materialtype.MaterialType
import com.exatech.ios.api.productaccessory.ProductAccessory
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.Hibernate
import javax.persistence.*

@Table(name="product")
@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "productId")
data class Product
(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="product_id")
        val productId: Int,

        /* material_type_id ManyToOne */
        @OneToMany(cascade=[CascadeType.ALL], fetch=FetchType.LAZY, mappedBy="product")
        @JsonManagedReference
        val productAccessories: List<ProductAccessory>,

        @ManyToOne(fetch=FetchType.LAZY, cascade=[CascadeType.REFRESH])
        @JoinColumn(name="material_type_id")
        val materialType: MaterialType,

        @Column(name="name")
        val name: String,
        @Column(name="size")
        val size: Int,
        @Column(name="sku")
        val sku: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Product

        return productId != null && productId == other.productId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(productId = $productId )"
    }
}
