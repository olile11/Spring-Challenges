package ia.deepblue.poi.domain

import jakarta.persistence.*
import java.util.*

@Entity
data class InterestPoint(
    @Enumerated(EnumType.ORDINAL)
    val name: PointType,
    @Embedded
    val coordinate: Coordinate,
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
)

@Embeddable
data class Coordinate(
    var x: Double,
    var y: Double
)