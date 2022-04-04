package com.exatech.ios.api.productionmaterial

import com.exatech.ios.api.color.Color
import com.exatech.ios.api.materialtype.MaterialType
import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Table(name="production_material_audit")
@Entity
data class ProductionMaterial
(
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="production_material_id")
    val productionMaterialId: Int,

    @ManyToOne(fetch=FetchType.LAZY, cascade=[CascadeType.REFRESH])
    @JoinColumn(name="color_id")
    val color: Color,

    @ManyToOne(fetch=FetchType.LAZY, cascade=[CascadeType.REFRESH])
    @JoinColumn(name="material_type_id")
    val materialType: MaterialType,

    //TODO Create DAO and relationships
    @Column(name="manufacturer")
    val manufacturer: String,

    @Column(name="amount")
    val amount: Double,
    @Column(name="name") //TODO Auto capitalize upon insert or update
    val name: String,
    @CreationTimestamp
    @Column(name="date_inserted",  nullable=false, updatable=false)
    var dateInserted: LocalDateTime
)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as ProductionMaterial

        return productionMaterialId != null && productionMaterialId == other.productionMaterialId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(productionMaterialId = $productionMaterialId )"
    }
}
