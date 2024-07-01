package mk.ru.shop.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator

@Entity
data class Transaction(
    @Id
    @UuidGenerator
    var id: UUID? = null,
    @Column(nullable = false)
    var amount: BigDecimal? = BigDecimal.ZERO,
    @Column(nullable = false)
    @CreationTimestamp
    var date: LocalDateTime? = LocalDateTime.now(),
    @ManyToOne(targetEntity = Wallet::class, fetch = FetchType.LAZY)
    var sender: Wallet? = null,
    @ManyToOne(targetEntity = Wallet::class, fetch = FetchType.LAZY)
    var recipient: Wallet? = null,
    @OneToOne(targetEntity = Product::class, fetch = FetchType.LAZY)
    var product: Product? = null
)
