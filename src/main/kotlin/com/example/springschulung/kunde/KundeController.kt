package com.example.springschulung.kunde

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/kunde")
class KundeController(private val kundeService: KundeService) {

    /*
    ####### Einstiegsübung Controller #######

    Erweitert den KundeController um einen weiteren Endpunkt “/kunde/nachname/{nachname}”
        - Der Endpunkt soll Kunden mit dem passenden Nachnamen zurückliefern
        - Es existiert bereits eine Funktion im KundeService die ihr benutzen könnt
        - Um KundeRepository braucht ihr euch nicht kümmern
        - Ruft euren Endpunkt auf
     */

    @GetMapping
    fun getAllKunden(): ResponseEntity<List<KundeEntity>> {
        return ResponseEntity.status(HttpStatus.OK).body(kundeService.getAllKunden())
    }

    @GetMapping("/{kundennummer}")
    fun getKundeByKundennummer(@PathVariable kundennummer: Int): ResponseEntity<KundeEntity?> {
        val kunde = kundeService.getKundeByKundennummer(kundennummer)
        return if (kunde != null) {
            ResponseEntity.status(HttpStatus.OK).body(kunde)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }

    @GetMapping("/nachname/{nachname}")
    fun getKundeByNachname(@PathVariable nachname: String): List<KundeEntity> {
        return kundeService.getKundeByNachname(nachname)
    }

    @GetMapping("/starting-with/{character}")
    fun getAllKundenStartingWith(@PathVariable character: Char): List<KundeEntity> {
        return kundeService.getAllKundeStartingWith(character)
    }

    @PostMapping
    fun createKunde(@RequestBody body: CreateKundeDto): ResponseEntity<KundeEntity?> {
        val kunde = kundeService.createKunde(body)

        return if (kunde != null) {
            ResponseEntity.status(HttpStatus.CREATED).body(kunde)
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }

    @PutMapping("/{kundennummer}")
    fun updateKunde(@RequestBody body: CreateKundeDto, @PathVariable kundennummer: Int): ResponseEntity<KundeEntity> {
        val kunde = kundeService.updateKunde(body, kundennummer)
        return ResponseEntity.status(HttpStatus.OK).body(kunde)
    }

    @DeleteMapping("/{kundennummer}")
    fun deleteKunde(@PathVariable kundennummer: Int): ResponseEntity<Unit> {
        kundeService.deleteKunde(kundennummer)
        return ResponseEntity.ok().build()
    }
}
