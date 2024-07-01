package mk.ru.shop.exceptions

import org.springframework.http.HttpStatus

class ContentNotFoundException(override val message: String) :
    BaseException(message = message, status = HttpStatus.NO_CONTENT)