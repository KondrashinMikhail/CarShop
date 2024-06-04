package mk.ru.carshop.services.criteria.conditions

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import mk.ru.carshop.enums.CriteriaOperation
import mk.ru.carshop.services.criteria.specifications.PredicateSpecification

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    visible = true,
    property = "field"
)
@JsonSubTypes(
    Type(value = StringCondition::class, name = "manufacturer"),
    Type(value = StringCondition::class, name = "model"),
    Type(value = LocalDateCondition::class, name = "registrationDate"),
    Type(value = BigDecimalCondition::class, name = "price"),
    Type(value = BooleanCondition::class, name = "deleted"),
)
abstract class CommonCondition<T>(
    open val field: String,
    open val operation: CriteriaOperation,
    open val value: T,
    @JsonIgnore
    val predicateSpecification: PredicateSpecification<T>
)
