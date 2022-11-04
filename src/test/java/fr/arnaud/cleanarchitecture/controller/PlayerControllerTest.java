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
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.PlayerDto;



public class PlayerControllerTest extends AbstractTest {

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .serializers(LOCAL_DATETIME_SERIALIZER)
            .serializationInclusion(JsonInclude.Include.NON_NULL).build();
    
    @Test
    public void crudPlayer() throws Exception {
    	
    	TokenDto userToken = loginUser();
    	TokenDto adminToken = loginAdmin();

    	//delete unknown object
        this.mockMvc.perform(MockMvcRequestBuilders
        	.delete("/v1/players/" + UUID.randomUUID().toString())
    		.headers(getHeaders(adminToken)))
    		.andExpect(MockMvcResultMatchers.status().isNotFound());

        //create one player
        UUID uuid = UUID.randomUUID();
    	PlayerDto player = new PlayerDto(uuid, "arnaud", "barbe");
        String json = this.mapper.writeValueAsString(player);
        
        this.mockMvc.perform(MockMvcRequestBuilders
        	.post("/v1/players")
	        .content(json)
	        .contentType(MediaType.APPLICATION_JSON)
	        .accept(MediaType.APPLICATION_JSON)
    		.headers(getHeaders(adminToken)))
	        .andExpect(MockMvcResultMatchers.status().isCreated());

        //check if player was correctly created
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/players/" + uuid.toString())
        	.headers(getHeaders(userToken)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        
        assertEquals(player, this.mapper.readValue(contentAsString, PlayerDto.class));

        //update the player
        player = new PlayerDto(uuid, "arnaud", "lecrubier");
        
        json = this.mapper.writeValueAsString(player);
        
        this.mockMvc.perform(MockMvcRequestBuilders
        	.put("/v1/players/" + uuid.toString())
	        .content(json)
	        .contentType(MediaType.APPLICATION_JSON)
        	.headers(getHeaders(adminToken)))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
        
        //check if player was correctly updated
        resultActions = this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/players/" + uuid.toString())
        	.headers(getHeaders(userToken)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        result = resultActions.andReturn();
        contentAsString = result.getResponse().getContentAsString();
        
        assertEquals(player, this.mapper.readValue(contentAsString, PlayerDto.class));

        //delete the player
        this.mockMvc.perform(MockMvcRequestBuilders
        	.delete("/v1/players/" + uuid.toString())
        	.headers(getHeaders(adminToken)));

        //verify that the player is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/players/" + uuid.toString())
        	.headers(getHeaders(userToken)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	PlayerDto player1 = new PlayerDto(uuid, "arnaud", "barbe");
        
        json = this.mapper.writeValueAsString(player1);
 
    	//create 2 players
        this.mockMvc.perform(MockMvcRequestBuilders
    		.post("/v1/players")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .headers(getHeaders(adminToken)))
            .andExpect(MockMvcResultMatchers.status().isCreated());
        
    	uuid = UUID.randomUUID();
    	PlayerDto player2 = new PlayerDto(uuid, "christophe", "lambert");
        
        json = this.mapper.writeValueAsString(player2);
 
        this.mockMvc.perform(MockMvcRequestBuilders
    		.post("/v1/players")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .headers(getHeaders(adminToken)))
            .andExpect(MockMvcResultMatchers.status().isCreated());
        
        //check if getPlayers return 2 players
        this.mockMvc.perform(MockMvcRequestBuilders
    		.get("/v1/players")
    		.contentType(MediaType.APPLICATION_JSON)
    		.accept(MediaType.APPLICATION_JSON)
    		.headers(getHeaders(adminToken)))
    		.andExpect(jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.status().isOk());
        
        this.mockMvc.perform(MockMvcRequestBuilders
    		.delete("/v1/players/" + player1.id().toString())
    		.headers(getHeaders(adminToken)));
        
        this.mockMvc.perform(MockMvcRequestBuilders
        	.delete("/v1/players/" + player2.id().toString())
        	.headers(getHeaders(adminToken)));

        this.mockMvc.perform(MockMvcRequestBuilders
        		.get("/v1/players")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON)
        		.headers(getHeaders(userToken)))
        		.andExpect(jsonPath("$", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}

