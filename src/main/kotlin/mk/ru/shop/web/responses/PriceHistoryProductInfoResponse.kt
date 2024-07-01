package mk.ru.shop.web.responses

import java.time.LocalDate
import java.util.UUID

data class PriceHistoryProductInfoResponse(
    val id: UUID,
    val name: String,
    val description: String,
    val registrationDate: LocalDate,
)
