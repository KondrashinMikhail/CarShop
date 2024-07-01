package mk.ru.shop.mappers

import mk.ru.shop.persistence.entities.Product
import mk.ru.shop.utils.CommonFunctions
import mk.ru.shop.web.requests.ProductCreateRequest
import mk.ru.shop.web.responses.ProductCreateResponse
import mk.ru.shop.web.responses.ProductInfoResponse
import mk.ru.shop.web.responses.ProductUpdateResponse
import org.springframework.stereotype.Component

@Component
class ProductMapper(
    private val appUserMapper: AppUserMapper
) {
    fun toCreateResponse(product: Product): ProductCreateResponse = ProductCreateResponse(
        id = product.id!!,
        registrationDate = product.registrationDate!!
    )

    fun toUpdateResponse(product: Product): ProductUpdateResponse = ProductUpdateResponse(
        id = product.id!!,
        name = product.name!!,
        description = product.description!!,
        actualPrice = CommonFunctions.getActualPrice(product)
    )

    fun toInfoResponse(product: Product): ProductInfoResponse = ProductInfoResponse(
        id = product.id!!,
        name = product.name!!,
        description = product.description,
        registrationDate = product.registrationDate!!,
        deleted = product.deleted!!,
        actualPrice = CommonFunctions.getActualPrice(product),
        owner = appUserMapper.toProductInfoResponse(product.owner!!)
    )

//    fun toPriceHistoryInfoResponse(product: Product): PriceHistoryProductInfoResponse = PriceHistoryProductInfoResponse(
//        id = product.id!!,
//        name = product.name!!,
//        description = product.description!!,
//        registrationDate = product.registrationDate!!,
//    )

    fun toEntity(product: ProductCreateRequest): Product = Product(
        name = product.name,
        description = product.description,
    )
}