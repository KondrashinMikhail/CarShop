package mk.ru.shop.services.wallet

import mk.ru.shop.persistence.entities.AppUser
import mk.ru.shop.persistence.entities.Wallet

interface WalletService {
    fun create(owner: AppUser): Wallet
}