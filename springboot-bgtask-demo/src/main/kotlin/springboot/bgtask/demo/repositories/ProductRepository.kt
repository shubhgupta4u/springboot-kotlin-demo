package springboot.bgtask.demo.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import springboot.bgtask.demo.models.Product

@Repository
interface ProductRepository : JpaRepository<Product, Long>