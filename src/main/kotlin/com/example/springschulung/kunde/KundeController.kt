package com.example.springschulung.kunde

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
    fun getAllKunden(): List<KundeEntity> {
        return kundeService.getAllKunden()
    }

    @GetMapping("/{kundennummer}")
    fun getKundeByKundennummer(@PathVariable kundennummer: Int): KundeEntity? {
        return kundeService.getKundeByKundennummer(kundennummer)
    }

    @GetMapping("/nachname/{nachname}")
    fun getKundeByNachname(@PathVariable nachname: String): List<KundeEntity> {
        return kundeService.getKundeByNachname(nachname)
    }

    @GetMapping("/starting-with/{character}")
    fun getAllKundenStartingWith(@PathVariable character: Char): List<KundeEntity> {
        return kundeService.getAllKundeStartingWith(character)
    }
}
