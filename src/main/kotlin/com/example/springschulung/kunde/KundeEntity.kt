package com.example.springschulung.kunde

import jakarta.persistence.*

@Entity
@Table(name = "KUNDE")
data class KundeEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val kundennummer: Int?,

    @Column
    val vorname: String,

    @Column
    val nachname: String
)
