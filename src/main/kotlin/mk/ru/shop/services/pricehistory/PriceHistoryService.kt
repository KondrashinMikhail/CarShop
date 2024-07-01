package mk.ru.shop.services.pricehistory

import java.util.UUID
import mk.ru.shop.persistence.entities.PriceHistory
import mk.ru.shop.services.criteria.conditions.Condition
import mk.ru.shop.web.requests.PriceHistoryCreateRequest
import mk.ru.shop.web.responses.PriceHistoryInfoResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PriceHistoryService {
    fun create(priceHistoryCreateRequest: PriceHistoryCreateRequest): PriceHistory
    fun searchPriceHistory(productId: UUID, conditions: List<Condition<Any>>?, pageable: Pageable?): Page<PriceHistoryInfoResponse>
}