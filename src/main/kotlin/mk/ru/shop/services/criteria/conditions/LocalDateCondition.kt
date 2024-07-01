package mk.ru.shop.services.criteria.conditions

import java.time.LocalDate
import mk.ru.shop.enums.CriteriaOperation
import mk.ru.shop.services.criteria.specifications.LocalDatePredicateSpecification

data class LocalDateCondition(
    override val field: String,
    override val operation: CriteriaOperation,
    override val value: LocalDate,
) : Condition<LocalDate>(
    field = field,
    operation = operation,
    value = value,
    predicateSpecification = LocalDatePredicateSpecification()
)
