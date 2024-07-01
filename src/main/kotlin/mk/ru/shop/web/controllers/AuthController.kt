package mk.ru.shop.web.controllers

import mk.ru.shop.services.authentication.AuthenticationService
import mk.ru.shop.services.token.TokenService
import mk.ru.shop.web.requests.AuthenticationRequest
import mk.ru.shop.web.responses.AuthenticationResponse
import mk.ru.shop.web.responses.RefreshTokenResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationService: AuthenticationService,
    private val tokenService: TokenService
) {
    @PostMapping("/login")
    fun authenticate(@RequestBody authRequest: AuthenticationRequest): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(authenticationService.authenticate(authRequest))

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshToken: String): ResponseEntity<RefreshTokenResponse> =
        tokenService.refreshAccessToken(refreshToken)
            ?.let { ResponseEntity.ok(it) }
            ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid refresh token.")

}