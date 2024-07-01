package mk.ru.shop.mappers

import mk.ru.shop.persistence.entities.PriceHistory
import mk.ru.shop.web.requests.PriceHistoryCreateRequest
import mk.ru.shop.web.responses.PriceHistoryInfoResponse
import org.springframework.stereotype.Component

@Component
class PriceHistoryMapper(
//    private val productMapper: ProductMapper,
    private val appUserMapper: AppUserMapper
) {
    fun toEntity(createRequest: PriceHistoryCreateRequest): PriceHistory = PriceHistory(
        product = createRequest.product,
        appUser = createRequest.appUser,
        price = createRequest.price,
    )

    fun toInfoResponse(priceHistory: PriceHistory): PriceHistoryInfoResponse = PriceHistoryInfoResponse(
        id = priceHistory.id!!,
        price = priceHistory.price!!,
        date = priceHistory.date!!,
//        product = productMapper.toPriceHistoryInfoResponse(priceHistory.product!!),
        user = appUserMapper.toPriceHistoryInfoResponse(priceHistory.appUser!!)
    )
}