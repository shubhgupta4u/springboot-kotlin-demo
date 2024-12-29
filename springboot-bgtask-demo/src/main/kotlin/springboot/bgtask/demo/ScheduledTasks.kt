package springboot.bgtask.demo

import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import org.slf4j.LoggerFactory
import springboot.bgtask.demo.services.TaskService
import springboot.bgtask.demo.tasks.ProductQuantityMonitor

@Component
class ScheduledTasks(@Autowired private val taskService: TaskService, @Autowired private val productQuantityMonitor: ProductQuantityMonitor) {

    private val logger = LoggerFactory.getLogger(ScheduledTasks::class.java)

    @Value("\${task.one.cron}")
    private lateinit var taskOneCron: String

    @Value("\${task.two.cron}")
    private lateinit var taskTwoCron: String

    @Value("\${task.three.cron}")
    private lateinit var taskThreeCron: String

    @Value("\${task.outofstockmonitor.cron}")
    private lateinit var taskOutOfStockMonitorCron: String

    @Scheduled(cron = "\${task.one.cron}")
    fun taskOne() {
        logger.info("Task One executed at: {}", LocalDateTime.now())
        println("Task One executed at: ${LocalDateTime.now()}")
    }

    @Scheduled(cron = "\${task.two.cron}")
    fun taskTwo() {
        logger.info("Task Two executed at: {}", LocalDateTime.now())
        println("Task Two executed at: ${LocalDateTime.now()}")
    }

    @Scheduled(cron = "\${task.three.cron}")
    fun taskThree() {
        logger.info("Task Three executed at: {}", LocalDateTime.now())
        println("Task Three executed at: ${LocalDateTime.now()}")
    }

    @Scheduled(cron = "\${task.outofstockmonitor.cron}")
    fun taskOutOfStockMonitor() {
        logger.info("Task Out of stock monitor executed at: {}", LocalDateTime.now())
        println("Task Out of stock monitor executed at: ${LocalDateTime.now()}")
        taskService.executeTask(productQuantityMonitor)
    }
}

