package springboot.api.demo.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import springboot.api.demo.models.Product
import springboot.api.demo.services.ProductService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid;
import jakarta.annotation.PostConstruct
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.GrantedAuthority
import org.springframework.web.bind.annotation.GetMapping

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "Endpoints for managing products")
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)

    @PostConstruct
    fun init() {
        println("ProductController initialized.")
    }
    private fun getCurrentUser(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication.name
    }
    private fun printAuthentication() {
        val authentication = SecurityContextHolder.getContext().authentication
        val roles = authentication.authorities.map(GrantedAuthority::getAuthority)
        
        println("Roles: ${roles}")
    }
    /**
     * Create a new product.
     *
     * @param product the product to create
     * @return the ResponseEntity with status 200 (OK) and with body of the new product
     */
    @PostMapping
    @Operation(summary = "Create Product", description = "Save a new product")
    // @PreAuthorize("hasRole('ADMIN')")
    fun saveProduct(@Valid @RequestBody product: Product): ResponseEntity<Product> {
        try{
            logger.info("Request to save product: {}", product)
        val newProduct = productService.saveProduct(product)
        logger.info("Product saved successfully: {}", newProduct)
        return ResponseEntity.ok(newProduct)
         }
         catch (e: Exception) {
            logger.error("Error saving product by id: {}",product, e)
            return ResponseEntity.status(500).build()
        }        
    }

    /**
     * Get all products.
     *
     * @return the ResponseEntity with status 200 (OK) and with body of the list of products
     */
    @GetMapping
    @Operation(summary = "Get All Products", description = "Retrieve a list of all products")
    // @PreAuthorize("hasRole('ADMIN')")
    fun getAllProducts(): List<Product> {
        logger.info("Request to get all product from {}",getCurrentUser())
        printAuthentication()
        return productService.getAllProducts()
    }

    /**
     * Get a product by ID.
     *
     * @param id the ID of the product to get
     * @return the ResponseEntity with status 200 (OK) and with body of the product, or with status 404 (Not Found) if the product does not exist
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Product Detail By Id", description = "Search a product by identifier")
    // @PreAuthorize("hasRole('ADMIN')")
    fun getProductById(@PathVariable id: Long): ResponseEntity<Product> {
        logger.info("Request to get product by id : {}", id)
        val product = productService.getProductById(id)
        if (product != null) {
            logger.info("Found product: {}", product)
            return ResponseEntity.ok(product)
        } else {
            logger.warn("No Found product by id: {}", id)
            return ResponseEntity.notFound().build()
        }
    }


    /**
     * Update a product by ID.
     *
     * @param id the ID of the product to update
     * @param product the updated product
     * @return the ResponseEntity with status 200 (OK) and with body of the updated product, or with status 404 (Not Found) if the product does not exist
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update Product Detail", description = "Update a product by identifier")
    // @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    fun updateProduct(@PathVariable id: Long, @Valid @RequestBody product: Product): ResponseEntity<Product> {
        try{
            logger.info("Request to update product by id : {} -> [{}]", id, product)
            val updatedProduct = productService.updateProduct(id, product)
            logger.info("Product updated successfully: {}", updatedProduct)
            return ResponseEntity.ok(updatedProduct) 
         }
         catch (e: Exception) {
            logger.error("Error updating product by id: {} -> [{}]", id, product, e)
            return ResponseEntity.status(500).build()
        }        
    }

    /**
     * Delete a product by ID.
     *
     * @param id the ID of the product to delete
     * @return the ResponseEntity with status 200 (OK) and with body of the message "Product deleted successfully"
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Product", description = "Delete a product by identifier")
    // @PreAuthorize("hasRole('ADMIN')")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<String> {
        try{
            logger.info("Request to delete product by id: {}", id)
            productService.deleteProduct(id)
            logger.info("Product deleted successfully by id: {}", id)
            return ResponseEntity.ok("Product deleted successfully")
        } catch (e: Exception) {
            logger.error("Error deleting product by id: {}", id, e)
            return ResponseEntity.notFound().build()
        }
        
    }
}
