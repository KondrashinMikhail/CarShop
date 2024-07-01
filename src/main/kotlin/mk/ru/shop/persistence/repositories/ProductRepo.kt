package mk.ru.shop.persistence.repositories

import java.util.UUID
import mk.ru.shop.persistence.entities.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ProductRepo : JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    fun findByDeletedFalseAndOwnerLogin(login: String, pageable: Pageable?): Page<Product>
    fun findByDeletedFalseAndOwnerLoginAndSellingTrue(login: String, pageable: Pageable?): Page<Product>
    fun findByOwnerLoginAndSellingTrue(login: String, pageable: Pageable?): Page<Product>
    fun findByOwnerLogin(login: String, pageable: Pageable?): Page<Product>
    fun findByDeletedFalse(pageable: Pageable?): Page<Product>
    fun findByDeletedFalseAndSellingTrue(pageable: Pageable?): Page<Product>
    fun findBySellingTrue(pageable: Pageable?): Page<Product>
}