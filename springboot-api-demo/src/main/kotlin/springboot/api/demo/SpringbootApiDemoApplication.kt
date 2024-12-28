package springboot.api.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["springboot.api.demo"])
class SpringbootApiDemoApplication

fun main(args: Array<String>) {
	runApplication<SpringbootApiDemoApplication>(*args)
}
