package com.example.fibonacci;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class KnownNumberControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  // Calculo el valor para un 0 <= n <= 90
  @Test
  public void calculateOk() throws Exception {
    
    mockMvc.perform(MockMvcRequestBuilders
            .post("/api/fibonacci/calculate/{number}", 10))
            .andExpect(status().isOk())
            .andExpect(result -> {
              String response = result.getResponse().getContentAsString();
              JsonNode body = objectMapper.readTree(response);
              int res = body.get("data").get("result").asInt();
              assertEquals(55, res);
            });
  }

  // Caso para un n < 0
  @Test
  public void calculateLesserThan0() throws Exception {
    
    mockMvc.perform(MockMvcRequestBuilders
            .post("/api/fibonacci/calculate/{number}", -10))
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String response = result.getResponse().getContentAsString();
              JsonNode body = objectMapper.readTree(response);
              String res = body.get("message").asText();
              assertEquals(Utils.ERROR_NEGATIVE_NUMBER, res);
            });
  }

  // Caso para un n > 90
  @Test
  public void calculateGreatherThan90() throws Exception {
    
    mockMvc.perform(MockMvcRequestBuilders
            .post("/api/fibonacci/calculate/{number}", 100))
            .andExpect(status().isBadRequest())
            .andExpect(result -> {
              String response = result.getResponse().getContentAsString();
              JsonNode body = objectMapper.readTree(response);
              String res = body.get("message").asText();
              assertEquals(Utils.ERROR_GREATHER_90, res);
            });
  }

  // Obtener stadisticas
  @Test
  public void getStatisticsOk() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/fibonacci/statistics"))
            .andExpect(status().isOk());
    
  }
}
