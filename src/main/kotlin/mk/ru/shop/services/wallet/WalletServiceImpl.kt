package mk.ru.shop.services.wallet

import mk.ru.shop.persistence.entities.AppUser
import mk.ru.shop.persistence.entities.Wallet
import mk.ru.shop.persistence.repositories.WalletRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class WalletServiceImpl(
    private val walletRepo: WalletRepo,
) : WalletService {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    override fun create(owner: AppUser): Wallet {
        val savedWallet: Wallet = walletRepo.save(Wallet(owner = owner))
        log.info("Created wallet with id - ${savedWallet.id} for user - ${owner.login}")
        return savedWallet
    }
}