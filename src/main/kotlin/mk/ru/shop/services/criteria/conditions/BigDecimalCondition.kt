package mk.ru.shop.services.criteria.conditions

import java.math.BigDecimal
import mk.ru.shop.enums.CriteriaOperation
import mk.ru.shop.services.criteria.specifications.BigDecimalPredicateSpecification

data class BigDecimalCondition(
    override val field: String,
    override val operation: CriteriaOperation,
    override val value: BigDecimal,
) : Condition<BigDecimal>(
    field = field,
    operation = operation,
    value = value,
    predicateSpecification = BigDecimalPredicateSpecification()
)