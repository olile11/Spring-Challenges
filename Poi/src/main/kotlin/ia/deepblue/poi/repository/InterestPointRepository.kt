package ia.deepblue.poi.repository

import ia.deepblue.poi.domain.InterestPoint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InterestPointRepository: JpaRepository<InterestPoint, String> {
}