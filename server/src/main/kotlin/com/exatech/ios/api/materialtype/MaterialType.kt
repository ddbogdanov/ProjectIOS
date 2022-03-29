package com.exatech.ios.api.materialtype

import org.hibernate.Hibernate
import javax.persistence.*

@Table(name="material_type")
@Entity
data class MaterialType
(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="material_type_id")
    val materialTypeId: Int,

    @Column(name="type")
    val type: String
)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as MaterialType

        return materialTypeId != null && materialTypeId == other.materialTypeId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(materialTypeId = $materialTypeId )"
    }

}
