package com.example.springschulung.kunde

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class KundeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Es koennen alle Kunden abgerufen werden`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/kunde").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
    }

    @Test
    fun `Ein Kunde kann per Kundennummer aufgerufen werden`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/kunde/1").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.kundennummer").value(1))
            .andExpect(jsonPath("$.vorname").value("Anna"))
            .andExpect(jsonPath("$.nachname").value("Albert"))
    }
}
