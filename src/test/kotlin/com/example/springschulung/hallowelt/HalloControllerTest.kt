package com.example.springschulung.hallowelt

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class HalloControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Hallo Welt`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/hallo").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().string("Hallo Welt!"))
    }

    @Test
    fun `Hallo Name`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/hallo/Max").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().string("Hallo Max!"))
    }
}
