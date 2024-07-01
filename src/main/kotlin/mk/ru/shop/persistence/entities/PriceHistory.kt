package mk.ru.shop.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator

@Entity
data class PriceHistory(
    @Id
    @UuidGenerator
    var id: UUID? = null,
    @Column(nullable = false)
    var price: BigDecimal? = BigDecimal.ZERO,
    @Column(nullable = false)
    @CreationTimestamp
    var date: LocalDateTime? = LocalDateTime.now(),
    @ManyToOne(targetEntity = Product::class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var product: Product? = null,
    @ManyToOne(targetEntity = AppUser::class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var appUser: AppUser? = null,
)
