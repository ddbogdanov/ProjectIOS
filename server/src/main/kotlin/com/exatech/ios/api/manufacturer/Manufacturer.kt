package com.exatech.ios.api.manufacturer

import org.hibernate.Hibernate
import javax.persistence.*

@Table(name="manufacturer")
@Entity
data class Manufacturer
(
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="manufacturer_id")
    val manufacturerId: Int,

    @Column(name="manufacturer")
    var manufacturer: String
)
{
    @PrePersist
    fun prePersist() = capitalize()

    @PreUpdate
    fun preUpdate() = capitalize()

    private fun capitalize() {
        this.manufacturer = manufacturer.uppercase()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Manufacturer

        return manufacturerId != null && manufacturerId == other.manufacturerId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(manufacturerId = $manufacturerId )"
    }
}
