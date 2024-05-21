package mk.ru.carshop.services.car

import jakarta.persistence.criteria.Predicate
import java.util.UUID
import mk.ru.carshop.exceptions.ContentNotFoundError
import mk.ru.carshop.exceptions.SellingException
import mk.ru.carshop.exceptions.SoftDeletionException
import mk.ru.carshop.mappers.CarMapper
import mk.ru.carshop.persistence.entities.Car
import mk.ru.carshop.persistence.repositories.CarRepository
import mk.ru.carshop.services.criteria.conditions.CommonCondition
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

    override fun findAll(
        conditions: List<CommonCondition<Any>>?,
        pageable: Pageable?
    ): Page<CarInfoResponse> {
        val specification: Specification<Car> = Specification<Car> { root, _, criteriaBuilder ->
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
        val cars: Page<Car> = carRepository.findAll(specification, pageable ?: Pageable.unpaged())
        log.info("Found ${cars.totalElements} of cars ${conditions?.let { "with ${it.size} of" } ?: "without"} conditions")
        return cars.map { carMapper.toInfoResponse(it) }
    }

    override fun findById(id: UUID): CarInfoResponse {
        val car: Car = findEntityById(id = id)
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
        val car = findEntityById(id = updateCarRequest.id, deletionCheck = true, soldCheck = true)
        updateCarRequest.manufacturer?.let { car.manufacturer = it }
        updateCarRequest.model?.let { car.model = it }
        updateCarRequest.price?.let { car.price = it }

        val updatedCar = carRepository.save(car)
        log.info("Updated car with id - ${updatedCar.id}")
        return carMapper.toInfoResponse(updatedCar)
    }

    override fun deleteCar(id: UUID) {
        val car = findEntityById(id = id, deletionCheck = true, soldCheck = true)
        car.deleted = true
        carRepository.save(car)
        log.info("Deleted car with id - $id")
    }

    override fun restoreCar(id: UUID) {
        val car = findEntityById(id = id)
        when (car.deleted) {
            true -> car.deleted = false
            false -> throw SoftDeletionException("Car with id - $id is not deleted")
        }
        carRepository.save(car)
        log.info("Restored car with id - $id")
    }

    override fun sellCar(id: UUID) {
        val car: Car = findEntityById(id = id, deletionCheck = true, soldCheck = true)
        car.sold = true
        carRepository.save(car)
        log.info("Sold car with id - $id")
    }

    private fun findEntityById(id: UUID, deletionCheck: Boolean = false, soldCheck: Boolean = false): Car {
        val car: Car = carRepository.findById(id).orElseThrow { ContentNotFoundError("Car with id - $id not found") }
        if (car.deleted && deletionCheck)
            throw SoftDeletionException("Car with id - $id not found")
        if (car.sold && soldCheck)
            throw SellingException("Car with id - $id is sold")
        return car
    }
}