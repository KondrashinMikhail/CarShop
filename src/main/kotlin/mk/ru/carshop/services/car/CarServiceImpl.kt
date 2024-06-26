package mk.ru.carshop.services.car

import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Predicate
import java.util.UUID
import mk.ru.carshop.enums.CriteriaOperations.EQUALS
import mk.ru.carshop.enums.CriteriaOperations.GREATER_THAN
import mk.ru.carshop.enums.CriteriaOperations.GREATER_THAN_OR_EQUAL
import mk.ru.carshop.enums.CriteriaOperations.LESS_THAN
import mk.ru.carshop.enums.CriteriaOperations.LESS_THAN_OR_EQUAL
import mk.ru.carshop.enums.CriteriaOperations.LIKE
import mk.ru.carshop.enums.CriteriaOperations.NOT_EQUALS
import mk.ru.carshop.exceptions.ContentNotFoundError
import mk.ru.carshop.exceptions.SoftDeleteException
import mk.ru.carshop.mappers.CarMapper
import mk.ru.carshop.persistence.entities.Car
import mk.ru.carshop.persistence.repositories.CarRepository
import mk.ru.carshop.services.criteria.conditions.CommonCondition
import mk.ru.carshop.services.criteria.specifications.PredicateSpecification
import mk.ru.carshop.web.requests.CreateCarRequest
import mk.ru.carshop.web.requests.UpdateCarRequest
import mk.ru.carshop.web.responses.CarInfoResponse
import mk.ru.carshop.web.responses.CreateCarResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class CarServiceImpl(
    private val carRepository: CarRepository,
    private val carMapper: CarMapper,
) : CarService {
    private final val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    override fun findAll(conditions: List<CommonCondition<Any>>?, pageable: Pageable): Page<CarInfoResponse> {
        val specification: Specification<Car> = Specification<Car> { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            conditions?.let {
                it.forEach { condition ->
                    val predicateSpecification: PredicateSpecification<Any> = condition.predicateSpecification
                    val expression: Expression<Any> = root.get(condition.field)
                    val value = condition.value
                    val predicate: Predicate = when (condition.operation) {
                        EQUALS -> predicateSpecification.equalPredicate(expression, value, criteriaBuilder)
                        NOT_EQUALS -> predicateSpecification.notEqualPredicate(expression, value, criteriaBuilder)
                        GREATER_THAN -> predicateSpecification.greaterThanPredicate(expression, value, criteriaBuilder)
                        GREATER_THAN_OR_EQUAL -> predicateSpecification.greaterThanOrEqualPredicate(expression, value, criteriaBuilder)
                        LESS_THAN -> predicateSpecification.lessThanPredicate(expression, value, criteriaBuilder)
                        LESS_THAN_OR_EQUAL -> predicateSpecification.lessThanOrEqualPredicate(expression, value, criteriaBuilder)
                        LIKE -> predicateSpecification.likePredicate(expression, value, criteriaBuilder)
                    }
                    predicates.add(predicate)
                }
                criteriaBuilder.and(* predicates.toTypedArray())
            }
        }
        val cars: Page<Car> = carRepository.findAll(specification, pageable)
        log.info("Found ${cars.totalElements} of cars ${conditions?.let { "with conditions" }}")
        return cars.map { carMapper.toInfoResponse(it) }
    }

    override fun findById(id: UUID): CarInfoResponse {
        val car: Car = findEntityById(id)
        log.info("Found car with id - $id")
        return carMapper.toInfoResponse(car)
    }

    override fun createCar(createCarRequest: CreateCarRequest): CreateCarResponse {
        val car = carMapper.toEntity(createCarRequest)
        val savedCar = carRepository.save(car)
        log.info("Created car with id - ${savedCar.id}")
        return carMapper.toCreateResponse(savedCar)
    }

    override fun updateCar(updateCarRequest: UpdateCarRequest): CarInfoResponse {
        val car = findEntityById(updateCarRequest.id)

        updateCarRequest.manufacturer?.let { car.manufacturer = it }
        updateCarRequest.model?.let { car.model = it }
        updateCarRequest.price?.let { car.price = it }

        val updatedCar = carRepository.save(car)
        log.info("Updated car with id - ${updatedCar.id}")
        return carMapper.toInfoResponse(updatedCar)
    }

    override fun deleteCar(id: UUID) {
        val car = findEntityById(id)
        when (car.isDeleted) {
            true -> throw SoftDeleteException("Car with id - $id not found")
            false -> car.isDeleted = true
        }
        carRepository.save(car)
        log.info("Deleted car with id - $id")
    }

    private fun findEntityById(id: UUID): Car {
        return carRepository.findById(id).orElseThrow { ContentNotFoundError("Car with id - $id not found") }
    }
}