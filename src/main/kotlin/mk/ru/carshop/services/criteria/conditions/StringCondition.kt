package mk.ru.carshop.services.criteria.conditions

import mk.ru.carshop.enums.CriteriaOperations
import mk.ru.carshop.services.criteria.specifications.StringPredicateSpecification

data class StringCondition(
    override val field: String,
    override val operation: CriteriaOperations,
    override val value: String,
) : CommonCondition<String>(
    field = field,
    operation = operation,
    value = value,
    predicateSpecification = StringPredicateSpecification()
)