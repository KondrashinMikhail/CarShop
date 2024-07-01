package mk.ru.shop.services.user

import mk.ru.shop.persistence.entities.AppUser
import mk.ru.shop.web.requests.AppUserRegisterRequest
import mk.ru.shop.web.requests.PasswordChangeRequest
import mk.ru.shop.web.responses.AppUserRegisterResponse

interface AppUserService {
    fun register(registerRequest: AppUserRegisterRequest): AppUserRegisterResponse
    fun changePassword(login: String, passwordChangeRequest: PasswordChangeRequest)
    fun block(login: String)
    fun restore(login: String)
    fun findEntityByLogin(login: String, blockedCheck: Boolean = false): AppUser
}