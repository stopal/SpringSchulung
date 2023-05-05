package com.example.springschulung.vertag

import java.time.LocalDate

data class CreateVertragDto(
    val vertragsArt: VertragsArt,
    val vertragsBeginn: LocalDate,
    val vertragsEnde: LocalDate
)
