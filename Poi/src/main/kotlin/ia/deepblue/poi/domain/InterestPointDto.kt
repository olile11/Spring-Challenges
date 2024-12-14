package ia.deepblue.poi.domain

class InterestPointDto (
    val name: PointType,
    val coordinate: Coordinate,
){
    companion object {
        fun toEntity(dto: InterestPointDto): InterestPoint{
            return InterestPoint(
                name = dto.name,
                coordinate = dto.coordinate)
        }

        fun fromEntity(entity: InterestPoint): InterestPointDto {
            return InterestPointDto(
                name = entity.name,
                coordinate = entity.coordinate
            )
        }
    }
}