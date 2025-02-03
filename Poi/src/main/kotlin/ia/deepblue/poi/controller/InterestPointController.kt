package ia.deepblue.poi.controller

import ia.deepblue.poi.domain.Coordinate
import ia.deepblue.poi.domain.InterestPointDto
import ia.deepblue.poi.service.InterestPointService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/poi")
class InterestPointController(
    private val interestPointService: InterestPointService,
) {
    @PostMapping("/create")
    fun createPoi(@RequestBody poiDto: InterestPointDto):
            ResponseEntity<InterestPointDto> {
        return ResponseEntity(
            interestPointService.createPoi(poiDto),
            HttpStatus.CREATED)
    }

    @GetMapping("/get")
    fun getPoi(): ResponseEntity<List<InterestPointDto>> {
        val response = interestPointService.findAll()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/point/{maxDistance}")
    fun getPoiByProximity(
        @PathVariable maxDistance: Double,
        @RequestParam x: Double,
        @RequestParam y: Double
    ):ResponseEntity<List<InterestPointDto>> {
        val response = interestPointService.findByProximity(
            maxDistance, Coordinate(x, y))
        return ResponseEntity.ok(response)
    }
}