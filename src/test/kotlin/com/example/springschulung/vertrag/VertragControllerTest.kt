package com.example.springschulung.vertrag

import com.example.springschulung.vertag.VertragEntity
import com.example.springschulung.vertag.VertragRepository
import com.example.springschulung.vertag.VertragsArt
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = ["classpath:reset-vertrag.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

// Wir könnten uns auch die initialen Daten für die Datenbank per sql Datei einpflegen
//@Sql(value = ["classpath:test-vertrag-data.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class VertragControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var vertragRepository: VertragRepository

    private val testVertrag = VertragEntity(
        null,
        VertragsArt.GAS,
        LocalDate.of(2022, 12, 1),
        LocalDate.of(2023, 12, 1)
    )

    // Einsetzen von einem Testsatz in die Datenbank
    // Steht für jede einzelne Testmethode zur verfügung
    @BeforeEach
    fun setup() {
        vertragRepository.save(testVertrag)
    }

    // Wir können auch einfach deleteAll() ausführen um die Datenbank zu leeren. Jedoch wird der Index damit nicht zurückgesetzt!
//    @AfterEach
//    fun teardown() {
//        vertragRepository.deleteAll()
//    }

    @Test
    fun `Es koennen alle Vertraege abgerufen werden`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/vertrag").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
    }

    @Test
    fun `Ein Vertrag kann per Vertragsnummer aufgerufen werden`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/vertrag/1").accept(MediaType.APPLICATION_JSON)
        )

            .andExpect(status().isOk)
            .andExpect(jsonPath("$.vertragnummer").value(1))
            .andExpect(jsonPath("$.vertragsArt").value("GAS"))
            .andExpect(jsonPath("$.vertragsBeginn").value("2022-12-01"))
            .andExpect(jsonPath("$.vertragsEnde").value("2023-12-01"))
    }

    @Test
    fun `Wir geben 404 zurueck wenn der angefragte Vertrag nicht existiert`() {
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/vertrag/11231").accept(MediaType.APPLICATION_JSON)
        )
            .andReturn()

        Assertions.assertEquals(result.response.status, 404)
        Assertions.assertEquals(result.response.contentAsString, "")
    }

    // Muss noch implementiert werden
//    @Test
//    fun `Vertraege koennen per VertragsArt aufgerufen werden`() {
//        val stromVertrag = VertragEntity(
//            null,
//            VertragsArt.STROM,
//            LocalDate.of(2000, 12, 1),
//            LocalDate.of(2001, 12, 1)
//        )
//
//        vertragRepository.save(stromVertrag)
//
//        mockMvc.perform(
//            MockMvcRequestBuilders.get("/vertrag/art/GAS").accept(MediaType.APPLICATION_JSON)
//        )
//            .andExpect(status().isOk)
//            .andExpect(jsonPath("$[0].vertragnummer").value(1))
//            .andExpect(jsonPath("$[0].vertragsArt").value("GAS"))
//            .andExpect(jsonPath("$[0].vertragsBeginn").value("2022-12-01"))
//            .andExpect(jsonPath("$[0].vertragsEnde").value("2023-12-01"))
//    }

    @Test
    fun `Ein neuer Vertrag kann erstellt werden`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/vertrag").accept(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "vertragsArt": "STROM",
                        "vertragsBeginn": "2022-12-01",
                        "vertragsEnde": "2023-12-01"
                    }
                    """.trim()
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.vertragsArt").value("STROM"))
            .andExpect(jsonPath("$.vertragsBeginn").value("2022-12-01"))
            .andExpect(jsonPath("$.vertragsEnde").value("2023-12-01"))


        mockMvc.perform(
            MockMvcRequestBuilders.get("/vertrag").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[1].vertragsArt").value("STROM"))
            .andExpect(jsonPath("$[1].vertragsBeginn").value("2022-12-01"))
            .andExpect(jsonPath("$[1].vertragsEnde").value("2023-12-01"))
    }

    @Test
    fun `Vertraege koennen verändert werden`() {
        mockMvc.perform(
            MockMvcRequestBuilders.put("/vertrag/1").accept(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "vertragsArt": "WASSER",
                        "vertragsBeginn": "2022-12-01",
                        "vertragsEnde": "2023-12-01"
                    }
                    """.trim()
                )
                .contentType(MediaType.APPLICATION_JSON)

        )
            .andExpect(status().isOk)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/vertrag/1").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.vertragsArt").value("WASSER"))
            .andExpect(jsonPath("$.vertragsBeginn").value("2022-12-01"))
            .andExpect(jsonPath("$.vertragsEnde").value("2023-12-01"))
    }

    @Test
    fun `Vertraege koennen geloescht werden`() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/vertrag/1").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/vertrag/1").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$").doesNotExist())
    }
}
