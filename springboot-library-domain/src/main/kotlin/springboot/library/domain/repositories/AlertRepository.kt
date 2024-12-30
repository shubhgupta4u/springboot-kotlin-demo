package springboot.library.domain.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import springboot.library.domain.models.*

@Repository
interface AlertRepository : JpaRepository<Alert, Long>