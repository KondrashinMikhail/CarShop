package mk.ru.shop.exceptions

import org.springframework.http.HttpStatus

class TokenException(override val message: String) : BaseException(message = message, status = HttpStatus.FORBIDDEN)
