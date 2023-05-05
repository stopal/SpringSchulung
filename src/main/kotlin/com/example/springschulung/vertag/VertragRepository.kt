package com.example.springschulung.vertag

import com.example.springschulung.kunde.KundeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface VertragRepository : JpaRepository<VertragEntity, String> {

    override fun findAll(): List<VertragEntity>


    fun save(kundeEntity: VertragEntity): VertragEntity?

    fun findByVertragsnummerAndKunde(vertragsnummer: Int, kunde: KundeEntity): VertragEntity?

    fun findAllByKunde(kunde: KundeEntity?): List<VertragEntity>
    fun deleteByVertragsnummerAndKunde(vertragsnummer: Int, kunde: KundeEntity)

}
