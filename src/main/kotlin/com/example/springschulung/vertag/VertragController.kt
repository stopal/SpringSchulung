package com.example.springschulung.vertag

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/kunde/{kundennummer}/vertrag")
class VertragController(private val vertragService: VertragService) {

    @GetMapping
    fun getAllVertraege(
        @PathVariable kundennummer: Int
    ): ResponseEntity<List<VertragEntity>> {
        return ResponseEntity.status(HttpStatus.OK).body(vertragService.getAllVertraege(kundennummer))
    }

    @GetMapping("/{vertragsnummer}")
    fun getVertragByVertragsnummer(
        @PathVariable vertragsnummer: Int,
        @PathVariable kundennummer: Int
    ): ResponseEntity<VertragEntity?> {
        val vertrag = vertragService.getVertragByVertragsnummer(vertragsnummer, kundennummer)
        return if (vertrag != null) {
            ResponseEntity.status(HttpStatus.OK).body(vertrag)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }

    @PostMapping
    fun createVertrag(
        @PathVariable kundennummer: Int,
        @RequestBody body: CreateVertragDto
    ): ResponseEntity<VertragEntity?> {
        val vertrag = vertragService.createVertrag(body, kundennummer)

        return if (vertrag != null) {
            ResponseEntity.status(HttpStatus.CREATED).body(vertrag)
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }

    @PutMapping("/{vertragsnummer}")
    fun updateVertrag(
        @RequestBody body: CreateVertragDto,
        @PathVariable vertragsnummer: Int,
        @PathVariable kundennummer: Int
    ): ResponseEntity<VertragEntity> {
        val vertrag = vertragService.updateVertrag(body, vertragsnummer, kundennummer)
        return ResponseEntity.status(HttpStatus.OK).body(vertrag)
    }

    @DeleteMapping("/{vertragsnummer}")
    fun deleteVertrag(
        @PathVariable vertragsnummer: Int,
        @PathVariable kundennummer: Int
    ): ResponseEntity<Unit> {
        vertragService.deleteVertrag(vertragsnummer, kundennummer)
        return ResponseEntity.ok().build()
    }
}
