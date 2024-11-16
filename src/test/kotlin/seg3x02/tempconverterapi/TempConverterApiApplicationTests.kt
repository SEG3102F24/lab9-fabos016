package seg3x02.tempconverterapi

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors

@WebMvcTest
class TempConverterApiApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Autowired
    lateinit var mockMvc: MockMvc

	@Test
    fun `should fail the user authentication`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/temperature-converter/fahrenheit-celsius/32"))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized())
    }

	@Test
    fun `should succeed the first user authentication for user1`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/temperature-converter/fahrenheit-celsius/32").with(SecurityMockMvcRequestPostProcessors.user("user1").password("pass1")))
            .andExpect(MockMvcResultMatchers.status().isOk())
    }

	@Test
    fun `should succeed the second user authentication for user2`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/temperature-converter/fahrenheit-celsius/32").with(SecurityMockMvcRequestPostProcessors.user("user2").password("pass2")))
            .andExpect(MockMvcResultMatchers.status().isOk())
    }

	@Test
    fun `should convert fahrenheit to celsius`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/temperature-converter/fahrenheit-celsius/32").with(SecurityMockMvcRequestPostProcessors.user("user1").password("pass1")))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("0.0"))
    }

	@Test
    fun `should convert celsius to fahrenheit`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/temperature-converter/celsius-fahrenheit/100").with(SecurityMockMvcRequestPostProcessors.user("user1").password("pass1")))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("212.0"))
    }
}
