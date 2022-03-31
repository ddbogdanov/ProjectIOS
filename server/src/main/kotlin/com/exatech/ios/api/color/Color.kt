package com.exatech.ios.api.color

import org.hibernate.Hibernate
import javax.persistence.*

@Table(name="color")
@Entity
data class Color
(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="color_id")
    val colorId: Int,

    @Column(name="color")
    val color: String
)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Color

        return colorId != null && colorId == other.colorId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(colorId = $colorId )"
    }
}
