package springboot.bgtask.demo.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import springboot.bgtask.demo.models.Alert

@Repository
interface AlertRepository : JpaRepository<Alert, Long>