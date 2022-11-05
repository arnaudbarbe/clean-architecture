package fr.arnaud.cleanarchitecture.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.arnaud.cleanarchitecture.AbstractTest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.TokenDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;



public class LeagueControllerTest extends AbstractTest {

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .serializers(LOCAL_DATETIME_SERIALIZER)
            .serializationInclusion(JsonInclude.Include.NON_NULL).build();
    
    @Test
    public void crudLeague() throws Exception {
    	
    	TokenDto userToken = loginUser();
    	TokenDto adminToken = loginAdmin();
    	
        //delete unknown object
        this.mockMvc.perform(
        		MockMvcRequestBuilders
        		.delete("/v1/leagues/" + UUID.randomUUID().toString())
        		.headers(getHeaders(adminToken)))
        		.andExpect(MockMvcResultMatchers.status().isNotFound());

        //create one league
        UUID uuid = UUID.randomUUID();
    	LeagueDto league = new LeagueDto(uuid, "Afebas");
        String json = this.mapper.writeValueAsString(league);
        
        this.mockMvc.perform(MockMvcRequestBuilders
        		.post("/v1/leagues")
		        .content(json)
		        .contentType(MediaType.APPLICATION_JSON)
		        .accept(MediaType.APPLICATION_JSON)
		        .headers(getHeaders(adminToken)))
		        .andExpect(MockMvcResultMatchers.status().isCreated());

        //check if league was correctly created
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
        		.get("/v1/leagues/" + uuid.toString())
        		.headers(getHeaders(adminToken)))
        		.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        
        assertEquals(league, this.mapper.readValue(contentAsString, LeagueDto.class));
        
        //update the league
        league = new LeagueDto(uuid, "FFB");
        
        json = this.mapper.writeValueAsString(league);
        
        this.mockMvc.perform(MockMvcRequestBuilders
        		.put("/v1/leagues/" + uuid.toString())
		        .content(json)
		        .contentType(MediaType.APPLICATION_JSON)
		        .headers(getHeaders(adminToken)))
		        .andExpect(MockMvcResultMatchers.status().isNoContent());
        
        //check if championship was correctly updated
        resultActions = this.mockMvc.perform(MockMvcRequestBuilders
        		.get("/v1/leagues/" + uuid.toString())
        		.headers(getHeaders(adminToken)))
        		.andExpect(MockMvcResultMatchers.status().isOk());
       
        result = resultActions.andReturn();
        contentAsString = result.getResponse().getContentAsString();
        
        assertEquals(league, this.mapper.readValue(contentAsString, LeagueDto.class));

        //delete the league
        this.mockMvc.perform(MockMvcRequestBuilders
        		.delete("/v1/leagues/" + uuid.toString())
        		.headers(getHeaders(adminToken)));

        //verify that the league is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders
        		.get("/v1/leagues/" + uuid.toString())
        		.headers(getHeaders(userToken)))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	LeagueDto league1 = new LeagueDto(uuid, "Afebas");
        
        json = this.mapper.writeValueAsString(league1);
 
    	//create 2 leagues
        this.mockMvc.perform(MockMvcRequestBuilders
        		.post("/v1/leagues")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(getHeaders(adminToken)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        
    	uuid = UUID.randomUUID();
    	LeagueDto league2 = new LeagueDto(uuid, "FBEP");
        
        json = this.mapper.writeValueAsString(league2);
 
        this.mockMvc.perform(MockMvcRequestBuilders
        		.post("/v1/leagues")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(getHeaders(adminToken)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        
        //check if getLeagues return 2 leagues
        this.mockMvc.perform(MockMvcRequestBuilders
        		.get("/v1/leagues")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON)
        		.headers(getHeaders(userToken)))
        		.andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
        this.mockMvc.perform(MockMvcRequestBuilders
        		.delete("/v1/leagues/" + league1.id().toString())
        		.headers(getHeaders(adminToken)));
        this.mockMvc.perform(MockMvcRequestBuilders
        		.delete("/v1/leagues/" + league2.id().toString())
        		.headers(getHeaders(adminToken)));
        
        this.mockMvc.perform(MockMvcRequestBuilders
        		.get("/v1/leagues")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON)
        		.headers(getHeaders(adminToken)))
        		.andExpect(jsonPath("$", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
        logoutAdmin(adminToken);
        logoutUser(userToken);

    }
}

