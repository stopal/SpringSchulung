package com.example.springschulung.kunde

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/kunde")
class KundeController(private val kundeService: KundeService) {

    /*
    ####### Einstiegsübung Controller #######

    Erstelle einen Endpunkt um Kunden per Nachnamen abzurufen.
     */


    @CrossOrigin
    @GetMapping
    fun getAllKunden(): List<KundeEntity> {
        return kundeService.getAllKunden()
    }

    @GetMapping("/{kundennummer}")
    fun getKundeByKundennummer(@PathVariable kundennummer: Int): KundeEntity? {
        return kundeService.getKundeByKundennummer(kundennummer)
    }
}
