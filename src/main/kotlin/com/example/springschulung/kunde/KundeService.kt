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

	Erstelle einen Endpunkt der alle Kunden zurückgibt deren Vornamen mit einem bestimmten Buchstaben anfangen.

	Der Buchstabe wird als Endpunktpfadparameter mitgegeben.

	Der KundeService sollte eine Funktion anbieten der alle Kunden aus der Datenbank ausließt aber nur die weitergibt die mit dem entsprechenden Buchstaben anfangen.
	Groß- und Kleinschreibweise ist irrelevant! (case insensitive)

	Hilfreich:
	Kotlin filter()
	https://kotlinlang.org/docs/collection-filtering.html#filter-by-predicate

	Kotlins startsWith()
	https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/starts-with.html
     */

}
