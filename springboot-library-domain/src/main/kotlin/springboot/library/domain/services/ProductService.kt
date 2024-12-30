package springboot.library.domain.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import springboot.library.domain.models.*
import springboot.library.domain.repositories.ProductRepository

@Service
class ProductService(@Autowired private val productRepository: ProductRepository) {

    fun getAllProducts(): List<Product> {
        return productRepository.findAll()
    }

    fun getProductById(id: Long): Product? {
        return productRepository.findById(id).orElse(null)
    }

    fun saveProduct(product: Product): Product {
        return productRepository.save(product)
    }

    fun updateProduct(id: Long, updatedProduct: Product): Product {
        val existingProduct = productRepository.findById(id)
        if (existingProduct.isPresent) {
            val product = existingProduct.get().apply {
                name = updatedProduct.name
                price = updatedProduct.price
                quantity = updatedProduct.quantity
            }
            return productRepository.save(product)
        } else {
            throw RuntimeException("Product not found")
        }
    }

    fun deleteProduct(id: Long) {
        productRepository.deleteById(id)
    }
}