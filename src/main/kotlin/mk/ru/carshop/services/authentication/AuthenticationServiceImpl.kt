package mk.ru.carshop.services.authentication

import java.util.Date
import mk.ru.carshop.configurations.JwtProperties
import mk.ru.carshop.services.token.TokenService
import mk.ru.carshop.services.user.AppUserDetails
import mk.ru.carshop.utils.Jwt
import mk.ru.carshop.web.requests.AuthenticationRequest
import mk.ru.carshop.web.responses.AuthenticationResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl(
    private val authManager: AuthenticationManager,
    private val userDetails: AppUserDetails,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties
) : AuthenticationService {
    override fun authentication(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.login,
                authenticationRequest.password
            )
        )
        val user: UserDetails = userDetails.loadUserByUsername(authenticationRequest.login)
        val accessToken: String = createAccessToken(user)
        val refreshToken: String = createRefreshToken(user)
        return AuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    override fun refreshAccessToken(refreshToken: String): String? {
        val subRefreshToken: String =
            if (refreshToken.startsWith(Jwt.PREFIX)) refreshToken.substringAfter(Jwt.PREFIX)
            else refreshToken

        val login: String = tokenService.getLoginFromToken(subRefreshToken)
        return login.let {
            val currentUserDetails: UserDetails = userDetails.loadUserByUsername(login)
            if (tokenService.isTokenValid(subRefreshToken, currentUserDetails)) createAccessToken(currentUserDetails)
            else null
        }
    }

    private fun createRefreshToken(user: UserDetails) = tokenService.generateToken(
        userDetails = user,
        expirationDate = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
    )

    override fun createAccessToken(user: UserDetails) = tokenService.generateToken(
        userDetails = user,
        expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
    )
}