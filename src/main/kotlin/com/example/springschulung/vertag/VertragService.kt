package com.example.springschulung.vertag

import com.example.springschulung.kunde.KundeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class VertragService(
    private val vertragsRepository: VertragRepository,
    private val kundeRepository: KundeRepository
) {

    fun getAllVertraege(kundennummer: Int): List<VertragEntity> {
        val kunde = kundeRepository.findByKundennummer(kundennummer)
        return vertragsRepository.findAllByKunde(kunde)
    }

    fun getVertragByVertragsnummer(vertragsnummer: Int, kundennummer: Int): VertragEntity? {
        val kunde = kundeRepository.findByKundennummer(kundennummer) ?: return null
        return vertragsRepository.findByVertragsnummerAndKunde(vertragsnummer, kunde)
    }

    fun createVertrag(body: CreateVertragDto, kundennummer: Int): VertragEntity? {
        val kunde = kundeRepository.findByKundennummer(kundennummer) ?: return null

        val neuerVertrag = VertragEntity(
            null,
            body.vertragsArt,
            body.vertragsBeginn,
            body.vertragsEnde,
            kunde
        )
        return vertragsRepository.save(neuerVertrag)
    }

    fun updateVertrag(body: CreateVertragDto, vertragsnummer: Int, kundennummer: Int): VertragEntity? {
        val kunde = kundeRepository.findByKundennummer(kundennummer) ?: return null

        val updatedVertrag = VertragEntity(
            vertragsnummer,
            body.vertragsArt,
            body.vertragsBeginn,
            body.vertragsEnde,
            kunde
        )
        return vertragsRepository.save(updatedVertrag)
    }

    @Transactional
    fun deleteVertrag(vertragsnummer: Int, kundennummer: Int) {
        val kunde = kundeRepository.findByKundennummer(kundennummer) ?: return
        vertragsRepository.deleteByVertragsnummerAndKunde(vertragsnummer, kunde)
    }
}
