package fr.arnaud.cleanarchitecture.controller;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.arnaud.cleanarchitecture.AbstractTest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.TokenDto;

public class AuthControllerTest extends AbstractTest {
    @Test
    public void badUserOnDeleteOperation() throws Exception {
    	
    	TokenDto userToken = loginUser();

        this.mockMvc.perform(
        		MockMvcRequestBuilders
        		.delete("/v1/championships/" + UUID.randomUUID().toString())
        		.headers(getHeaders(userToken)))
        		.andExpect(MockMvcResultMatchers.status().isForbidden());

        this.mockMvc.perform(
        		MockMvcRequestBuilders
        		.delete("/v1/leagues/" + UUID.randomUUID().toString())
        		.headers(getHeaders(userToken)))
        		.andExpect(MockMvcResultMatchers.status().isForbidden());

        this.mockMvc.perform(
        		MockMvcRequestBuilders
        		.delete("/v1/matchs/" + UUID.randomUUID().toString())
        		.headers(getHeaders(userToken)))
        		.andExpect(MockMvcResultMatchers.status().isForbidden());

        this.mockMvc.perform(
        		MockMvcRequestBuilders
        		.delete("/v1/players/" + UUID.randomUUID().toString())
        		.headers(getHeaders(userToken)))
        		.andExpect(MockMvcResultMatchers.status().isForbidden());

        this.mockMvc.perform(
        		MockMvcRequestBuilders
        		.delete("/v1/seasons/" + UUID.randomUUID().toString())
        		.headers(getHeaders(userToken)))
        		.andExpect(MockMvcResultMatchers.status().isForbidden());

        this.mockMvc.perform(
        		MockMvcRequestBuilders
        		.delete("/v1/teams/" + UUID.randomUUID().toString())
        		.headers(getHeaders(userToken)))
        		.andExpect(MockMvcResultMatchers.status().isForbidden());

   }


}
