package com.exatech.ios.api.productionmaterial.usage

import com.exatech.ios.api.color.Color
import com.exatech.ios.api.manufacturer.Manufacturer
import com.exatech.ios.api.materialtype.MaterialType
import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name="material_usage_audit")
data class MaterialUsageAudit
(
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="material_usage_audit_id")
    val muaId: Int,

    @ManyToOne(fetch= FetchType.LAZY, cascade=[CascadeType.REFRESH])
    @JoinColumn(name="color_id")
    val color: Color,

    @ManyToOne(fetch= FetchType.LAZY, cascade=[CascadeType.REFRESH])
    @JoinColumn(name="material_type_id")
    val materialType: MaterialType,

    @ManyToOne(fetch=FetchType.LAZY, cascade=[CascadeType.REFRESH])
    @JoinColumn(name="manufacturer_id")
    val manufacturer: Manufacturer,

    @Column(name="delta_amount")
    val deltaAmount: Double,

    @Column(name="date_performed",  nullable=false, updatable=false)
    var dateInserted: LocalDateTime
)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as MaterialUsageAudit

        return muaId != null && muaId == other.muaId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(muaId = $muaId )"
    }
}
