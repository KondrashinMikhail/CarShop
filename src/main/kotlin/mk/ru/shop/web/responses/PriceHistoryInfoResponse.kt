package mk.ru.shop.web.responses

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class PriceHistoryInfoResponse(
    val id: UUID,
    val price: BigDecimal,
    val date: LocalDateTime,
//    val product: PriceHistoryProductInfoResponse,
    val user: PriceHistoryAppUserInfoResponse
)
