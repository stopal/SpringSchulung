package com.example.springschulung.vertag

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class VertragService(private val vertragsRepository: VertragRepository) {

    fun getAllVertraege(): List<VertragEntity> {
        return vertragsRepository.findAll()
    }

    fun getVertragByVertragsnummer(vertragsnummer: Int): VertragEntity? {
        return vertragsRepository.findByVertragnummer(vertragsnummer).getOrNull()
    }

    fun createVertrag(body: CreateVertragDto): VertragEntity? {
        val neuerVertrag = VertragEntity(
            null,
            body.vertragsArt,
            body.vertragsBeginn,
            body.vertragsEnde

        )
        return vertragsRepository.save(neuerVertrag)
    }

    fun updateVertrag(body: CreateVertragDto, vertragsnummer: Int): VertragEntity? {
        val updatedVertrag = VertragEntity(
            vertragsnummer,
            body.vertragsArt,
            body.vertragsBeginn,
            body.vertragsEnde
        )
        return vertragsRepository.save(updatedVertrag)
    }

    fun deleteVertrag(vertragsnummer: Int) {
        vertragsRepository.deleteById(vertragsnummer.toString())
    }
}
