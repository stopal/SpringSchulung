package com.example.springschulung.vertag

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface VertragRepository : JpaRepository<VertragEntity, String> {

    override fun findAll(): List<VertragEntity>

    fun findByVertragnummer(vertragsnummer: Int): Optional<VertragEntity>

    fun save(kundeEntity: VertragEntity): VertragEntity?

}
