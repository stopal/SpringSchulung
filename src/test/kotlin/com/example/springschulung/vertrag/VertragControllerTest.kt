package com.example.springschulung.vertrag

import com.example.springschulung.kunde.KundeEntity
import com.example.springschulung.kunde.KundeRepository
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
@Sql(value = ["classpath:reset-kunde.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

// Wir könnten uns auch die initialen Daten für die Datenbank per sql Datei einpflegen
//@Sql(value = ["classpath:test-vertrag-data.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class VertragControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var kundeRepository: KundeRepository

    @Autowired
    private lateinit var vertragRepository: VertragRepository

    private lateinit var testKunde: KundeEntity
    private lateinit var testVertrag: VertragEntity


    // Einsetzen von einem Testsatz in die Datenbank
    // Steht für jede einzelne Testmethode zur verfügung
    @BeforeEach
    fun setup() {
        val kunde = kundeRepository.save(
            KundeEntity(
                null,
                "Max",
                "Mustermann",
                emptyList()
            )
        )!!

        testVertrag = vertragRepository.save(
            VertragEntity(
                null,
                VertragsArt.GAS,
                LocalDate.of(2022, 12, 1),
                LocalDate.of(2023, 12, 1),
                kunde
            )
        )!!

        testKunde = kundeRepository.save(
            kunde.copy(
                vertraege = listOf(testVertrag)
            )
        )!!
    }

    // Wir können auch einfach deleteAll() ausführen um die Datenbank zu leeren. Jedoch wird der Index damit nicht zurückgesetzt!
//    @AfterEach
//    fun teardown() {
//        vertragRepository.deleteAll()
//    }

    @Test
    fun `Es koennen alle Vertraege abgerufen werden von einen Kunden`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/kunde/${testKunde.kundennummer}/vertrag").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].vertragsnummer").value(1))
            .andExpect(jsonPath("$[0].vertragsArt").value("GAS"))
            .andExpect(jsonPath("$[0].vertragsBeginn").value("2022-12-01"))
            .andExpect(jsonPath("$[0].vertragsEnde").value("2023-12-01"))
    }

    @Test
    fun `Ein Vertrag von einem Kunden kann per Vertragsnummer aufgerufen werden`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/kunde/${testKunde.kundennummer}/vertrag/${testVertrag.vertragsnummer}")
                .accept(MediaType.APPLICATION_JSON)
        )

            .andExpect(status().isOk)
            .andExpect(jsonPath("$.vertragsnummer").value(1))
            .andExpect(jsonPath("$.vertragsArt").value("GAS"))
            .andExpect(jsonPath("$.vertragsBeginn").value("2022-12-01"))
            .andExpect(jsonPath("$.vertragsEnde").value("2023-12-01"))
    }

    @Test
    fun `Es koennen keine Vertraege abgerufen werden dem Kunden nicht gehoeren`() {
        // ein fremder Kunde
        val fremderKunde = kundeRepository.save(
            KundeEntity(
                null,
                "X",
                "Y",
                emptyList()
            )
        )

        // ein anderer Vertrag
        val fremderVertrag = vertragRepository.save(
            VertragEntity(
                null,
                VertragsArt.STROM,
                LocalDate.of(2022, 12, 1),
                LocalDate.of(2023, 12, 1),
                fremderKunde!!
            )
        )

        mockMvc.perform(
            MockMvcRequestBuilders.get("/kunde/${testKunde.kundennummer}/vertrag/${fremderVertrag!!.vertragsnummer}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
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

    @Test
    fun `Ein neuer Vertrag kann erstellt werden`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/kunde/${testKunde.kundennummer}/vertrag").accept(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "vertragsArt": "STROM",
                        "vertragsBeginn": "2022-12-01",
                        "vertragsEnde": "2030-12-01"
                    }
                    """.trim()
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.vertragsArt").value("STROM"))
            .andExpect(jsonPath("$.vertragsBeginn").value("2022-12-01"))
            .andExpect(jsonPath("$.vertragsEnde").value("2030-12-01"))

        mockMvc.perform(
            MockMvcRequestBuilders.get("/kunde/${testKunde.kundennummer}/vertrag").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[1].vertragsArt").value("STROM"))
            .andExpect(jsonPath("$[1].vertragsBeginn").value("2022-12-01"))
            .andExpect(jsonPath("$[1].vertragsEnde").value("2030-12-01"))
    }

    @Test
    fun `Vertraege koennen veraendert werden`() {
        mockMvc.perform(
            MockMvcRequestBuilders.put("/kunde/${testKunde.kundennummer}/vertrag/${testVertrag.vertragsnummer}")
                .accept(MediaType.APPLICATION_JSON)
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
            MockMvcRequestBuilders.get("/kunde/${testKunde.kundennummer}/vertrag/${testVertrag.vertragsnummer}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.vertragsArt").value("WASSER"))
            .andExpect(jsonPath("$.vertragsBeginn").value("2022-12-01"))
            .andExpect(jsonPath("$.vertragsEnde").value("2023-12-01"))
    }

    @Test
    fun `Vertraege koennen geloescht werden`() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/kunde/${testKunde.kundennummer}/vertrag/${testVertrag.vertragsnummer}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/kunde/${testKunde.kundennummer}/vertrag/${testVertrag.vertragsnummer}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$").doesNotExist())
    }
}
