package fr.arnaud.cleanarchitecture.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.arnaud.cleanarchitecture.AbstractTest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.TokenDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.SeasonDto;


public class SeasonControllerTest extends AbstractTest {

    @Test
    public void crudSeason() throws Exception {
    	
    	TokenDto userToken = loginUser();
    	TokenDto adminToken = loginAdmin();

    	//delete unknown object
        this.mockMvc.perform(MockMvcRequestBuilders
    		.delete("/v1/seasons/" + UUID.randomUUID().toString())
    		.headers(getHeaders(adminToken)))
    		.andExpect(MockMvcResultMatchers.status().isNotFound());

    	//create one season
        UUID uuid = UUID.randomUUID();
    	SeasonDto season = new SeasonDto(uuid, "2022/2023", LocalDateTime.of(2022, 9, 1, 0, 0, 0, 0), LocalDateTime.of(2023, 6, 30, 0, 0, 0, 0));
        String json = this.mapper.writeValueAsString(season);
        
        this.mockMvc.perform(MockMvcRequestBuilders
        	.post("/v1/seasons")
	        .content(json)
	        .contentType(MediaType.APPLICATION_JSON)
	        .accept(MediaType.APPLICATION_JSON)
    		.headers(getHeaders(adminToken)))
	        .andExpect(MockMvcResultMatchers.status().isCreated());

        //check if season was correcty created
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/seasons/" + uuid.toString())
        	.headers(getHeaders(adminToken)))
            .andExpect(MockMvcResultMatchers.status().isOk());
        
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        
        assertEquals(season, this.mapper.readValue(contentAsString, SeasonDto.class));

        
        //update the season
        season = new SeasonDto(uuid, "2021/2022", LocalDateTime.of(2022, 9, 1, 0, 0, 0, 0), LocalDateTime.of(2023, 6, 30, 0, 0, 0, 0));
        
        json = this.mapper.writeValueAsString(season);
        
        this.mockMvc.perform(MockMvcRequestBuilders
        	.put("/v1/seasons/" + uuid.toString())
	        .content(json)
	        .contentType(MediaType.APPLICATION_JSON)
	        .headers(getHeaders(adminToken)))
	        .andExpect(MockMvcResultMatchers.status().isNoContent());
        
        //check if season was correctly updated
        resultActions = this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/seasons/" + uuid.toString())
        	.headers(getHeaders(userToken)))
            .andExpect(MockMvcResultMatchers.status().isOk());
        
        result = resultActions.andReturn();
        contentAsString = result.getResponse().getContentAsString();
        
        assertEquals(season, this.mapper.readValue(contentAsString, SeasonDto.class));

         //delete the season
        this.mockMvc.perform(MockMvcRequestBuilders
        	.delete("/v1/seasons/" + uuid.toString())
        	.headers(getHeaders(adminToken)));

        //verify that the season is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/seasons/" + uuid.toString())
        	.headers(getHeaders(userToken)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	SeasonDto season1 = new SeasonDto(uuid, "2022/2023", LocalDateTime.of(2022, 9, 1, 0, 0, 0, 0), LocalDateTime.of(2023, 6, 30, 0, 0, 0, 0));
        
        json = this.mapper.writeValueAsString(season1);
 
    	//create 2 seasons
        this.mockMvc.perform(MockMvcRequestBuilders
    		.post("/v1/seasons")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .headers(getHeaders(adminToken)))
            .andExpect(MockMvcResultMatchers.status().isCreated());
        
    	uuid = UUID.randomUUID();
    	SeasonDto season2 = new SeasonDto(uuid, "2023/2024", LocalDateTime.of(2023, 9, 1, 0, 0, 0, 0), LocalDateTime.of(2024, 6, 30, 0, 0, 0, 0));
        
        json = this.mapper.writeValueAsString(season2);
 
        this.mockMvc.perform(MockMvcRequestBuilders
    		.post("/v1/seasons")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .headers(getHeaders(adminToken)))
            .andExpect(MockMvcResultMatchers.status().isCreated());
        
        //check if getSeasons return 2 seasons
        this.mockMvc.perform(MockMvcRequestBuilders
    		.get("/v1/seasons")
    		.contentType(MediaType.APPLICATION_JSON)
    		.accept(MediaType.APPLICATION_JSON)
    		.headers(getHeaders(userToken)))
    		.andExpect(jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.status().isOk());
        
        this.mockMvc.perform(MockMvcRequestBuilders
        	.delete("/v1/seasons/" + season1.id().toString())
        	.headers(getHeaders(adminToken)));
        this.mockMvc.perform(MockMvcRequestBuilders
        	.delete("/v1/seasons/" + season2.id().toString())
        	.headers(getHeaders(adminToken)));
        this.mockMvc.perform(MockMvcRequestBuilders
    		.get("/v1/seasons")
    		.contentType(MediaType.APPLICATION_JSON)
    		.accept(MediaType.APPLICATION_JSON)
    		.headers(getHeaders(adminToken)))
    		.andExpect(jsonPath("$", Matchers.hasSize(0)))
            .andExpect(MockMvcResultMatchers.status().isOk());

    }
}

