package ia.deepblue.poi.service

import ia.deepblue.poi.domain.Coordinate
import ia.deepblue.poi.domain.InterestPointDto
import ia.deepblue.poi.repository.InterestPointRepository
import org.springframework.stereotype.Service
import kotlin.math.pow
import kotlin.math.sqrt

@Service
class InterestPointService(
    private val interestPointRepository: InterestPointRepository,
) {
    fun createPoi(poiDto: InterestPointDto): InterestPointDto {
        val savedPoint = interestPointRepository
            .save(InterestPointDto.toEntity(poiDto))
        return InterestPointDto.fromEntity(savedPoint)
    }
    fun findAll(): List<InterestPointDto> {
        val response = interestPointRepository.findAll()
        return response.map { InterestPointDto.fromEntity(it) }.toList()
    }
    fun findByProximity(
        max: Double,
        ref: Coordinate): List<InterestPointDto> {
        var response = interestPointRepository.findAll()
        response = response.filter{ getDistance(ref, it.coordinate) <= max }.toList()
        return response.map { InterestPointDto.fromEntity(it) }.toList()
    }

    fun getDistance(
        p: Coordinate,
        q: Coordinate) = sqrt((p.x - q.x).pow(2) + (p.y - q.y).pow(2))
}