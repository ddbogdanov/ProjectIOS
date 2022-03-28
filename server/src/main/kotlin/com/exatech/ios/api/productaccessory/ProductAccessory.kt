package com.exatech.ios.api.productaccessory

import com.exatech.ios.api.product.Product
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIdentityInfo

import com.fasterxml.jackson.annotation.ObjectIdGenerators
import org.hibernate.Hibernate
import javax.persistence.*

@Table(name="product_accessory")
@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "productAccessoryId")
data class ProductAccessory
(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="product_accessory_id")
        val productAccessoryId: Int,

        /*ManyToOne materialtype*/
        @ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="product_id")
        @JsonBackReference // I hate you i hate you i hate you i hate you i hate you
        val product: Product,

        @Column(name="name")
        val name: String,
        @Column(name="size")
        val size: Int
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
                other as ProductAccessory

                return productAccessoryId != null && productAccessoryId == other.productAccessoryId
        }

        override fun hashCode(): Int = javaClass.hashCode()

        @Override
        override fun toString(): String {
                return this::class.simpleName + "(productAccessoryId = $productAccessoryId )"
        }
}
