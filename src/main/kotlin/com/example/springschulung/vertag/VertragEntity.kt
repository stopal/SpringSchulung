package com.example.springschulung.vertag

import com.example.springschulung.kunde.KundeEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "VERTRAG")
data class VertragEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val vertragnummer: Int?,

    @Column
    val vertragsArt: VertragsArt,

    @Column
    val vertragsBeginn: LocalDate,

    @Column
    val vertragsEnde: LocalDate,

    @ManyToOne
    @JoinColumn(name = "kundennummer")
    val kunde: KundeEntity
)
