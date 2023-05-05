package com.example.springschulung.kunde

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class KundeServiceTest {
    private val kundeRepository = mockk<KundeRepository>()

    @Test
    fun `getAllKundeStartingWith() returns correct Kunde when the character matches`() {
        val kundeMatches = KundeEntity(1, "Max", "Mustermann", emptyList())
        val kundeNotMatching = KundeEntity(1, "Peter", "Peterson", emptyList())
        val list = listOf(kundeMatches, kundeNotMatching)
        val kundeService = KundeService(kundeRepository)
        every { kundeRepository.findAll() } returns list

        val result = kundeService.getAllKundeStartingWith('M')

        assertThat(result).isEqualTo(listOf(kundeMatches))
        assertThat(result).doesNotContain(kundeNotMatching)
    }

    @Test
    fun `getAllKundeStartingWith() return an empty list when it doesnt match`() {
        val kunde = KundeEntity(1, "Max", "Mustermann", emptyList())
        val kundeService = KundeService(kundeRepository)
        every { kundeRepository.findAll() } returns listOf(kunde)

        val result = kundeService.getAllKundeStartingWith('A')

        assertThat(result).isEmpty()
    }
}
