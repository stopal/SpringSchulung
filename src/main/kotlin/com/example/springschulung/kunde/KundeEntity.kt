package com.example.springschulung.kunde

import com.example.springschulung.vertag.VertragEntity
import com.fasterxml.jackson.annotation.JsonIgnore
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
    val nachname: String,

    @OneToMany(mappedBy = "kunde")
    @JsonIgnore
    val vertraege: List<VertragEntity>
)
