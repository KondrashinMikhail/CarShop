package mk.ru.shop.web.requests

import java.math.BigDecimal
import mk.ru.shop.persistence.entities.AppUser
import mk.ru.shop.persistence.entities.Product

data class PriceHistoryCreateRequest(
    val product: Product,
    val appUser: AppUser,
    val price: BigDecimal
)