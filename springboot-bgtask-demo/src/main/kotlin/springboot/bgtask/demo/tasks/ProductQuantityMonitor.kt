package springboot.bgtask.demo.tasks

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import springboot.bgtask.demo.models.*
import springboot.bgtask.demo.services.*
import org.slf4j.LoggerFactory

@Component
class ProductQuantityMonitor @Autowired constructor(
    private val productService: ProductService,
    private val alertService: AlertService
) : Runnable {

    private val logger = LoggerFactory.getLogger(ProductQuantityMonitor::class.java)

    override fun run() {
        val products = productService.getAllProducts()
        for (product in products) {
            if (product.quantity == 0) {
                logger.warn("Product ${product.name} is out of stock!")
                alertService.saveAlert(Alert(source=AlertSource.ProductQuantityMonitor,type=AlertType.OutOfStock,title="Product ${product.name} is out of stock!"))
            }
        }
    }
}