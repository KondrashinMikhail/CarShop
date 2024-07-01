package mk.ru.shop.configurations

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.jwt")
data class JwtProperties(
    val prefix: String,
    val header: String,
    val secret: String,
    val accessTokenExpiration: Long,
    val refreshTokenExpiration: Long
)
