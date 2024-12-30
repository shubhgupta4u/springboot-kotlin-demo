package library.domain.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import library.domain.models.*

@Repository
interface ProductRepository : JpaRepository<Product, Long>