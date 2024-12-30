package library.domain.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import library.domain.repositories.*
import library.domain.models.Alert

@Service
class AlertService(@Autowired private val alertRepository: AlertRepository) {

    fun getAllAlerts(): List<Alert> {
        return alertRepository.findAll()
    }

    fun getAlertById(id: Long): Alert? {
        return alertRepository.findById(id).orElse(null)
    }

    fun saveAlert(alert: Alert): Alert {
        return alertRepository.save(alert)
    }

    fun deleteAlert(id: Long) {
        alertRepository.deleteById(id)
    }
}