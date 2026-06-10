package net.hackyourfuture.backend.week6.postify.controllers;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserStatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void  getUserStats_existingUser_returnsOkAndBody() throws Exception{
        mockMvc.perform(get("/users/1/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.userName").value("lena_v"))
                .andExpect(jsonPath("$.userCountry").value("NL"))
                .andExpect(jsonPath("$.totalStreams").isNumber());
    }

    @Test
    void getUserStats_nonExistingUser_returns404() throws Exception {
        mockMvc.perform(get("/users/9999/stats"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with id 9999 not found"));

    }
}