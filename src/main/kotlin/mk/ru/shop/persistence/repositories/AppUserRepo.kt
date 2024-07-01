package mk.ru.shop.persistence.repositories

import mk.ru.shop.persistence.entities.AppUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface AppUserRepo : JpaRepository<AppUser, String>, JpaSpecificationExecutor<AppUser> {
    fun existsByLoginOrMail(login: String, mail: String): Boolean
}
