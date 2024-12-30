package springboot.library.domain.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.io.Serializable

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null,

    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    @field:NotNull(message = "Price cannot be null")
    @field:Positive(message = "Price must be greater than 0")
    var price: Double,

    @Column(nullable = false)
    @field:NotNull(message = "Quantity cannot be null")
    @field:Positive(message = "Quantity must be greater than 0")
    var quantity: Int
): Serializable
