package com.example.springschulung.kunde

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class KundeService(private val kundeRepository: KundeRepository) {

    fun getAllKunden(): List<KundeEntity> {
        return kundeRepository.findAll()
    }

    fun getKundeByKundennummer(kundennummer: Int): KundeEntity? {
        return kundeRepository.findByKundennummer(kundennummer).getOrNull()
    }

    fun getKundeByNachname(nachname: String): List<KundeEntity> {
        return kundeRepository.findByNachname(nachname)
    }


    /*
    ####### Einstiegsübung Service #######

    - Der KundeService dient als Bindeglied zwischen dem Controller und dem Repository
    - Erstelle einen Endpunkt, der alle Kunden zurückgibt, deren Vornamen mit einem bestimmten Buchstaben anfangen.
        - Der Buchstabe soll als Pfadparameter mitgegeben werden.
    - Der KundeService sollte eine Funktion anbieten der alle Kunden aus der Datenbank ausliest aber nur die weitergibt die mit dem entsprechenden Buchstaben anfangen.
    -    Groß- und Kleinschreibweise ist irrelevant! (case insensitive)
    - Hilfreiche Funktionen:
        - filter()
        - https://kotlinlang.org/docs/collection-filtering.html#filter-by-predicate
        - startsWith()
        - https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/starts-with.html
        - Testet den neuen Endpunkt mit verschiedenen Werten
     */

    fun getAllKundeStartingWith(char: Char): List<KundeEntity> {
        val all = kundeRepository.findAll()
        return all.filter { it.vorname.startsWith(char, true) }
    }

}
