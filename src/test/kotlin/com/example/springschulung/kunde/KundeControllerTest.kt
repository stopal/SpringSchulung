package com.example.springschulung.kunde

import org.junit.jupiter.api.Assertions
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

    @Test
    fun `Wir geben 404 zurueck wenn der angefragte Kunde nicht existiert`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/kunde/11231").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$").doesNotExist())
    }

    @Test
    fun `Wir geben 404 zurück wenn der angefragte Kunde nicht existiert 2`() {
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/kunde/11231").accept(MediaType.APPLICATION_JSON)
        )
            .andReturn()

        Assertions.assertEquals(result.response.status, 404)
        Assertions.assertEquals(result.response.contentAsString, "")
    }

    @Test
    fun `Kunden koennen per Nachname aufgerufen werden`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/kunde/nachname/Albert").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].kundennummer").value(1))
            .andExpect(jsonPath("$[0].vorname").value("Anna"))
            .andExpect(jsonPath("$[0].nachname").value("Albert"))
    }

    @Test
    fun `Kunden koennen mit dem Anfangsbuchstaben des Nachnamens gefunden werden`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/kunde/starting-with/A").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].kundennummer").value(1))
            .andExpect(jsonPath("$[0].vorname").value("Anna"))
            .andExpect(jsonPath("$[0].nachname").value("Albert"))
    }

    @Test
    fun `Ein neuer Kunde kann erstellt werden`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/kunde").accept(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "vorname": "Peter",
                        "nachname": "Pan"
                    }
                    """.trim()
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.vorname").value("Peter"))
            .andExpect(jsonPath("$.nachname").value("Pan"))


        mockMvc.perform(
            MockMvcRequestBuilders.get("/kunde/nachname/Pan").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].vorname").value("Peter"))
            .andExpect(jsonPath("$[0].nachname").value("Pan"))

    }
}
