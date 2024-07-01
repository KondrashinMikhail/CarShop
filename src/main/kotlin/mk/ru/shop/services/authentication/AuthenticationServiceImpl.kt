package mk.ru.shop.services.authentication

import mk.ru.shop.services.token.TokenService
import mk.ru.shop.services.user.AppUserDetailsService
import mk.ru.shop.web.requests.AuthenticationRequest
import mk.ru.shop.web.responses.AuthenticationResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl(
    private val authManager: AuthenticationManager,
    private val appUserDetailsService: AppUserDetailsService,
    private val tokenService: TokenService,
) : AuthenticationService {
    override fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.login,
                authenticationRequest.password
            )
        )
        val user: UserDetails = appUserDetailsService.loadUserByUsername(authenticationRequest.login)
        val accessToken: String = tokenService.createAccessToken(user)
        val refreshToken: String = tokenService.createRefreshToken(user)
        return AuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}