package mk.ru.shop.utils

import jakarta.persistence.criteria.Predicate
import java.math.BigDecimal
import mk.ru.shop.persistence.entities.Product
import mk.ru.shop.services.criteria.conditions.Condition
import org.springframework.data.jpa.domain.Specification

object CommonFunctions {
    fun getActualPrice(product: Product): BigDecimal =
        product.priceHistory!!.sortedBy { it.date }.reversed().first().price!!

    fun <T> createSpecification(conditions: List<Condition<Any>>?): Specification<T> =
        Specification<T> { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            conditions?.forEach { condition ->
                predicates.add(
                    condition.operation.getPredicate(
                        predicateSpecification = condition.predicateSpecification,
                        expression = root.get(condition.field),
                        value = condition.value,
                        criteriaBuilder = criteriaBuilder
                    )
                )
            }
            criteriaBuilder.and(* predicates.toTypedArray())
        }
}