package mk.ru.shop.web.filters

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mk.ru.shop.configurations.JwtProperties
import mk.ru.shop.services.token.TokenService
import mk.ru.shop.services.user.AppUserDetailsService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val userDetailsService: AppUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader: String? = request.getHeader(jwtProperties.header)

        if (authHeader == null || !authHeader.startsWith(jwtProperties.prefix)) {
            filterChain.doFilter(request, response)
            return
        }

        val jwtToken = authHeader.substringAfter(jwtProperties.prefix)
        val login = tokenService.getLoginFromToken(jwtToken)

        if (SecurityContextHolder.getContext().authentication == null) {
            val foundUser = userDetailsService.loadUserByUsername(login)
            if (tokenService.isTokenValid(jwtToken, foundUser)) updateContext(foundUser, request)
            filterChain.doFilter(request, response)
        }
    }

    private fun updateContext(foundUser: UserDetails, request: HttpServletRequest) {
        val authToken = UsernamePasswordAuthenticationToken(foundUser, null, foundUser.authorities)
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authToken
    }
}