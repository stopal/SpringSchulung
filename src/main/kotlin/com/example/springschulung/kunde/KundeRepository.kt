package com.example.springschulung.kunde

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface KundeRepository : JpaRepository<KundeEntity, String> {

    override fun findAll(): List<KundeEntity>

    fun findByKundennummer(kundennummer: Int): Optional<KundeEntity>

    fun findByNachname(nachname: String): List<KundeEntity>

    /*
    ####### Einstiegsübung Repository #######

    - Erstelle einen neuen Endpunkt, um neue Kunden anzulegen
    - Erweitere KundeRepository um die save() Funktion
    - Die neue Endpunktmethode braucht die korrekte @*Mapping Annotation. Welche HTTP Methode wäre das?
    - Die Endpunktmethode hat einen Funktionsparameter die mit @RequestBody annotiert werden muss
    - Erstellt eine neue DTO-Klasse, die als Datentyp dient für die Bodyvariable. Welche Parameter brauchen wir, um einen neuen Kunden zu erstellen?
    - Die Bodyvariable könnt ihr dem KundeService weiterreichen damit dieser daraus eine KundeEntity erstellt und die save funktion aufruft
    - Testet den Endpunkt und erstellt neue Kundeneinträge
    */

    fun save(kundeEntity: KundeEntity): KundeEntity?

}
