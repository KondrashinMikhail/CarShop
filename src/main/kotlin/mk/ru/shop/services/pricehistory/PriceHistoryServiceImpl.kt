package mk.ru.shop.services.pricehistory

import jakarta.persistence.criteria.Join
import java.util.UUID
import mk.ru.shop.mappers.PriceHistoryMapper
import mk.ru.shop.persistence.entities.PriceHistory
import mk.ru.shop.persistence.entities.Product
import mk.ru.shop.persistence.repositories.PriceHistoryRepo
import mk.ru.shop.services.criteria.conditions.Condition
import mk.ru.shop.utils.CommonFunctions
import mk.ru.shop.web.requests.PriceHistoryCreateRequest
import mk.ru.shop.web.responses.PriceHistoryInfoResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class PriceHistoryServiceImpl(
    private val priceHistoryRepo: PriceHistoryRepo,
    private val priceHistoryMapper: PriceHistoryMapper
) : PriceHistoryService {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    override fun create(priceHistoryCreateRequest: PriceHistoryCreateRequest): PriceHistory {
        val savedPriceHistory: PriceHistory =
            priceHistoryRepo.save(priceHistoryMapper.toEntity(priceHistoryCreateRequest))

        log.info(
            "Created price history with id - ${savedPriceHistory.id} " +
                    "for product with id - ${priceHistoryCreateRequest.product.id} " +
                    "by user with id - ${priceHistoryCreateRequest.appUser.login}"
        )
        return savedPriceHistory
    }

    override fun searchPriceHistory(
        productId: UUID,
        conditions: List<Condition<Any>>?,
        pageable: Pageable?
    ): Page<PriceHistoryInfoResponse> {
        val specification: Specification<PriceHistory> = CommonFunctions.createSpecification(conditions)

        val additionalSpec: Specification<PriceHistory> = Specification<PriceHistory> { root, _, criteriaBuilder ->
            val productJoin: Join<PriceHistory, Product> = root.join("product")
            criteriaBuilder.equal(productJoin.get<UUID>("id"), productId)
        }

        val priceHistories: Page<PriceHistoryInfoResponse> =
            priceHistoryRepo.findAll(specification.and(additionalSpec), pageable ?: Pageable.unpaged())
                .map { priceHistoryMapper.toInfoResponse(it) }
        log.info("Found ${priceHistories.totalElements} of price histories ${conditions?.let { "with ${it.size} of" } ?: "without"} conditions")

        return priceHistories
    }
}