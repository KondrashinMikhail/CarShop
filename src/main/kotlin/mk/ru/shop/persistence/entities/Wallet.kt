package mk.ru.shop.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import org.hibernate.annotations.UuidGenerator

@Entity
data class Wallet(
    @Id
    @UuidGenerator
    var id: UUID? = null,
    @Column(nullable = false)
    var balance: BigDecimal = BigDecimal.ZERO,
    @Column(nullable = false)
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),
    @ManyToOne(targetEntity = AppUser::class, fetch = FetchType.LAZY)
    var owner: AppUser? = null,
)
