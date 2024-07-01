package mk.ru.shop.services.product

import java.util.UUID
import mk.ru.shop.services.criteria.conditions.Condition
import mk.ru.shop.web.requests.ProductCreateRequest
import mk.ru.shop.web.requests.ProductUpdateRequest
import mk.ru.shop.web.responses.ProductCreateResponse
import mk.ru.shop.web.responses.ProductInfoResponse
import mk.ru.shop.web.responses.ProductUpdateResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductService {
    fun search(conditions: List<Condition<Any>>?, pageable: Pageable?): Page<ProductInfoResponse>
    fun find(byOwner: Boolean?, showDeleted: Boolean?, onlySelling: Boolean?, pageable: Pageable?): Page<ProductInfoResponse>
    fun findById(id: UUID): ProductInfoResponse
    fun create(productCreateRequest: ProductCreateRequest): ProductCreateResponse
    fun update(productUpdateRequest: ProductUpdateRequest): ProductUpdateResponse
    fun delete(id: UUID)
    fun restore(id: UUID)
}