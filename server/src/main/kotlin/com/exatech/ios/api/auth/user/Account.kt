package com.exatech.ios.api.auth.user

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.Hibernate
import javax.persistence.*

@Table(name="user")
@Entity
data class Account
(
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private val userId: Int,
    @Column(name="username")
    val username: String,
    @Column(name="password")
    //@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    var password: String,
    @Column(name="role")
    val role: String
)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Account

        return userId != null && userId == other.userId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(userId = $userId )"
    }
}

