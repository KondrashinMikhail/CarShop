package mk.ru.shop.services.token

import java.util.Date
import mk.ru.shop.web.responses.RefreshTokenResponse
import org.springframework.security.core.userdetails.UserDetails

interface TokenService {
    fun generateToken(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean
    fun getLoginFromToken(token: String): String
    fun createAccessToken(user: UserDetails): String
    fun createRefreshToken(user: UserDetails): String
    fun refreshAccessToken(refreshToken: String): RefreshTokenResponse?
}