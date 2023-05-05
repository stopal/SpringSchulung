package com.example.springschulung.vertag

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/vertrag")
class VertragController(private val vertragService: VertragService) {

    @GetMapping
    fun getAllVertraege(): ResponseEntity<List<VertragEntity>> {
        return ResponseEntity.status(HttpStatus.OK).body(vertragService.getAllVertraege())
    }

    @GetMapping("/{vertragsnummer}")
    fun getVertragByVertragsnummer(@PathVariable vertragsnummer: Int): ResponseEntity<VertragEntity?> {
        val vertrag = vertragService.getVertragByVertragsnummer(vertragsnummer)
        return if (vertrag != null) {
            ResponseEntity.status(HttpStatus.OK).body(vertrag)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }

    @PostMapping
    fun createVertrag(@RequestBody body: CreateVertragDto): ResponseEntity<VertragEntity?> {
        val kunde = vertragService.createVertrag(body)

        return if (kunde != null) {
            ResponseEntity.status(HttpStatus.CREATED).body(kunde)
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }

    @PutMapping("/{vertragsnummer}")
    fun updateVertrag(
        @RequestBody body: CreateVertragDto,
        @PathVariable vertragsnummer: Int
    ): ResponseEntity<VertragEntity> {
        val kunde = vertragService.updateVertrag(body, vertragsnummer)
        return ResponseEntity.status(HttpStatus.OK).body(kunde)
    }

    @DeleteMapping("/{vertragsnummer}")
    fun deleteVertrag(@PathVariable vertragsnummer: Int): ResponseEntity<Unit> {
        vertragService.deleteVertrag(vertragsnummer)
        return ResponseEntity.ok().build()
    }
}
