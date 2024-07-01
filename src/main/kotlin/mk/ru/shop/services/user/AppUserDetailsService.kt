package mk.ru.shop.services.user

import mk.ru.shop.exceptions.ContentNotFoundException
import mk.ru.shop.exceptions.SoftDeletionException
import mk.ru.shop.persistence.entities.AppUser
import mk.ru.shop.persistence.repositories.AppUserRepo
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AppUserDetailsService(private val appUserRepo: AppUserRepo) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val appUser: AppUser =
            appUserRepo.findById(username!!)
                .orElseThrow { ContentNotFoundException("User with login - $username not found") }

        if (appUser.blocked)
            throw SoftDeletionException("User with login - $username is blocked")

        return User.builder()
            .username(appUser.login)
            .password(appUser.password)
            .roles(appUser.role.name)
            .accountLocked(appUser.blocked)
            .build()
    }
}