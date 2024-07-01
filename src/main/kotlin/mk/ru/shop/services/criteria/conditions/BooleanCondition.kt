package mk.ru.shop.services.criteria.conditions

import mk.ru.shop.enums.CriteriaOperation
import mk.ru.shop.services.criteria.specifications.BooleanPredicateSpecification

data class BooleanCondition(
    override val field: String,
    override val operation: CriteriaOperation,
    override val value: Boolean
) : Condition<Boolean>(
    field = field,
    operation = operation,
    value = value,
    predicateSpecification = BooleanPredicateSpecification()
)
