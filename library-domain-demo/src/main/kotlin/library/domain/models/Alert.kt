package library.domain.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.PrePersist
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.io.Serializable
import java.time.LocalDateTime

enum class AlertSource {
    ProductQuantityMonitor
}
enum class AlertType {
    OutOfStock
}

@Entity
@Table(name = "alerts")
data class Alert(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null,

    @field:NotNull(message = "Alert Source cannot be null")
    @Column(nullable = false)
    var type: AlertType,

    @field:NotNull(message = "Alert Type cannot be null")
    @Column(nullable = false)
    var source: AlertSource,

    @field:NotBlank(message = "Alert Title cannot be blank")
    @Column(nullable = false)
    var title: String,

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null
): Serializable
{
    @PrePersist
    fun setCreatedAt() {
        createdAt = LocalDateTime.now()
    }
}
