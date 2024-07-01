package mk.ru.shop.mappers

import mk.ru.shop.enums.AppUserRole
import mk.ru.shop.persistence.entities.AppUser
import mk.ru.shop.web.requests.AppUserRegisterRequest
import mk.ru.shop.web.responses.AppUserRegisterResponse
import mk.ru.shop.web.responses.PriceHistoryAppUserInfoResponse
import mk.ru.shop.web.responses.ProductAppUserInfoResponse
import org.springframework.stereotype.Component

@Component
class AppUserMapper {
    fun toEntity(user: AppUserRegisterRequest): AppUser = AppUser(
        login = user.login,
        password = user.password,
        mail = user.mail,
        role = AppUserRole.USER,
    )

    fun toRegisterResponse(appUser: AppUser): AppUserRegisterResponse = AppUserRegisterResponse(
        login = appUser.login!!,
        registrationDate = appUser.registrationDate!!
    )

    fun toProductInfoResponse(appUser: AppUser): ProductAppUserInfoResponse = ProductAppUserInfoResponse(
        login = appUser.login!!,
        mail = appUser.mail!!,
        blocked = appUser.blocked
    )

    fun toPriceHistoryInfoResponse(appUser: AppUser): PriceHistoryAppUserInfoResponse = PriceHistoryAppUserInfoResponse(
        login = appUser.login!!,
        mail = appUser.mail!!,
    )
}