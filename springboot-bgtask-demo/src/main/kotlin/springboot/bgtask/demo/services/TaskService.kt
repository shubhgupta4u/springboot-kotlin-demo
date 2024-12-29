package springboot.bgtask.demo.services

import org.springframework.stereotype.Service
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Service
class TaskService (private val taskExecutor: ThreadPoolTaskExecutor) {

    fun executeTask(myRunnableTask: Runnable) {
        taskExecutor.execute(myRunnableTask)
    }
}
