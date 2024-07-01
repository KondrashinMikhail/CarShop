package mk.ru.shop.services.criteria.conditions

import mk.ru.shop.enums.CriteriaOperation
import mk.ru.shop.services.criteria.specifications.StringPredicateSpecification

data class StringCondition(
    override val field: String,
    override val operation: CriteriaOperation,
    override val value: String,
) : Condition<String>(
    field = field,
    operation = operation,
    value = value,
    predicateSpecification = StringPredicateSpecification()
)
