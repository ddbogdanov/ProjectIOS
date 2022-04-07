package com.exatech.ios.api.productionmaterial.calculated

import com.exatech.ios.api.color.Color
import com.exatech.ios.api.manufacturer.Manufacturer
import com.exatech.ios.api.materialtype.MaterialType
import org.hibernate.Hibernate
import javax.persistence.*

@Table(name="production_material_calculated")
@Entity
data class CalculatedMaterial
(
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="p_m_calc_id")
    val productionMaterialCalculatedId: Int,

    @ManyToOne(fetch= FetchType.LAZY, cascade=[CascadeType.REFRESH])
    @JoinColumn(name="color_id")
    val color: Color,

    @ManyToOne(fetch= FetchType.LAZY, cascade=[CascadeType.REFRESH])
    @JoinColumn(name="material_type_id")
    val materialType: MaterialType,

    @ManyToOne(fetch=FetchType.LAZY, cascade=[CascadeType.REFRESH])
    @JoinColumn(name="manufacturer_id")
    val manufacturer: Manufacturer,

    @Column(name="amount_calculated")
    val amount: Double,
    @Column(name="name")
    val name: String,
)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CalculatedMaterial

        return productionMaterialCalculatedId != null && productionMaterialCalculatedId == other.productionMaterialCalculatedId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(productionMaterialCalculatedId = $productionMaterialCalculatedId )"
    }
}