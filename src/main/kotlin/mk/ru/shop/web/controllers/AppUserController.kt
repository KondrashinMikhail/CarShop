package mk.ru.shop.web.controllers

import mk.ru.shop.services.user.AppUserService
import mk.ru.shop.web.requests.AppUserRegisterRequest
import mk.ru.shop.web.requests.PasswordChangeRequest
import mk.ru.shop.web.responses.AppUserRegisterResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class AppUserController(private val appUserService: AppUserService) {
    @PostMapping("/register")
    fun register(@RequestBody appUserRegisterRequest: AppUserRegisterRequest): ResponseEntity<AppUserRegisterResponse> =
        ResponseEntity.ok(appUserService.register(appUserRegisterRequest))

    @PatchMapping("/{login}/change-password")
    fun changePassword(
        @PathVariable login: String,
        @RequestBody passwordChangeRequest: PasswordChangeRequest
    ): ResponseEntity<Unit> =
        ResponseEntity.ok(appUserService.changePassword(login = login, passwordChangeRequest = passwordChangeRequest))

    @DeleteMapping("/{login}/block")
    fun block(@PathVariable login: String): ResponseEntity<Unit> = ResponseEntity.ok(appUserService.block(login))

    @PatchMapping("/{login}/restore")
    fun restore(@PathVariable login: String): ResponseEntity<Unit> = ResponseEntity.ok(appUserService.restore(login))
}