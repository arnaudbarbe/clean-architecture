package fr.arnaud.cleanarchitecture.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
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
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.ChampionshipDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.PlayerDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.SeasonDto;




public class ChampionshipControllerTest extends AbstractTest {

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .serializers(LOCAL_DATETIME_SERIALIZER)
            .serializationInclusion(JsonInclude.Include.NON_NULL).build();
    
    @Test
    public void crudChampionship() throws Exception {
    	
    	TokenDto userToken = loginUser();
    	TokenDto adminToken = loginAdmin();

    	//delete unknown object, nothing should happen
        this.mockMvc.perform(MockMvcRequestBuilders
        	.delete("/v1/championships/" + UUID.randomUUID().toString())
    		.headers(getHeaders(adminToken)))
    		.andExpect(MockMvcResultMatchers.status().isNotFound());

        //create one championship
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
        
    	uuid = UUID.randomUUID();
    	PlayerDto player = new PlayerDto(uuid, "arnaud", "barbe");
    	json = this.mapper.writeValueAsString(player);
        this.mockMvc.perform(MockMvcRequestBuilders
        	.post("/v1/players")
	        .content(json)
	        .contentType(MediaType.APPLICATION_JSON)
	        .accept(MediaType.APPLICATION_JSON)
	        .headers(getHeaders(adminToken)))
	        .andExpect(MockMvcResultMatchers.status().isCreated());

    	uuid = UUID.randomUUID();
    	LeagueDto league = new LeagueDto(uuid, "Afebas");
    	uuid = UUID.randomUUID();
    	json = this.mapper.writeValueAsString(league);
        this.mockMvc.perform(MockMvcRequestBuilders
        	.post("/v1/leagues")
	        .content(json)
	        .contentType(MediaType.APPLICATION_JSON)
	        .accept(MediaType.APPLICATION_JSON)
	        .headers(getHeaders(adminToken)))
	        .andExpect(MockMvcResultMatchers.status().isCreated());
        
    	ChampionshipDto championship = new ChampionshipDto(uuid, "France", player, season, league);
    	
        json = this.mapper.writeValueAsString(championship);
        
        this.mockMvc.perform(MockMvcRequestBuilders
        	.post("/v1/championships")
	        .content(json)
	        .contentType(MediaType.APPLICATION_JSON)
	        .accept(MediaType.APPLICATION_JSON)
	        .headers(getHeaders(adminToken)))
	        .andExpect(MockMvcResultMatchers.status().isCreated());

        //check if championship was correctly created
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/championships/" + uuid.toString())
	        .headers(getHeaders(userToken)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        
        assertEquals(championship, this.mapper.readValue(contentAsString, ChampionshipDto.class));

        //update the championship
        championship = new ChampionshipDto(uuid, "USA", player, season, league);
        
        json = this.mapper.writeValueAsString(championship);
        
        this.mockMvc.perform(MockMvcRequestBuilders
        	.put("/v1/championships/" + uuid.toString())
	        .content(json)
	        .contentType(MediaType.APPLICATION_JSON)
	        .headers(getHeaders(adminToken)))
        	.andExpect(MockMvcResultMatchers.status().isNoContent());
        
        //check if championship was correctly updated
        resultActions = this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/championships/" + uuid.toString())
	        .headers(getHeaders(userToken)))
            .andExpect(MockMvcResultMatchers.status().isOk());
       
        result = resultActions.andReturn();
        contentAsString = result.getResponse().getContentAsString();
        
        assertEquals(championship, this.mapper.readValue(contentAsString, ChampionshipDto.class));
       
        //delete the championship
        this.mockMvc.perform(MockMvcRequestBuilders
    		.delete("/v1/championships/" + uuid.toString())
	        .headers(getHeaders(adminToken)));

        //verify that the championship is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/championships/" + uuid.toString())
	        .headers(getHeaders(userToken)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	ChampionshipDto championship1 = new ChampionshipDto(uuid, "France", player, season, league);
        
        json = this.mapper.writeValueAsString(championship1);
 
    	//create 2 championships
        this.mockMvc.perform(MockMvcRequestBuilders
    		.post("/v1/championships")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
	        .headers(getHeaders(adminToken)))
            .andExpect(MockMvcResultMatchers.status().isCreated());
        
    	uuid = UUID.randomUUID();
    	ChampionshipDto championship2 = new ChampionshipDto(uuid, "Belgium", player, season, league);
        
        json = this.mapper.writeValueAsString(championship2);
 
        this.mockMvc.perform(MockMvcRequestBuilders
    		.post("/v1/championships")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
	        .headers(getHeaders(adminToken)))
            .andExpect(MockMvcResultMatchers.status().isCreated());
        
        //check if getCampionships return 2 championships
        this.mockMvc.perform(MockMvcRequestBuilders
    		.get("/v1/championships")
    		.contentType(MediaType.APPLICATION_JSON)
    		.accept(MediaType.APPLICATION_JSON)
	        .headers(getHeaders(userToken)))
    		.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.status().isOk());
        
        this.mockMvc.perform(MockMvcRequestBuilders
        	.delete("/v1/championships/" + championship1.id().toString())
	        .headers(getHeaders(adminToken)));
        this.mockMvc.perform(MockMvcRequestBuilders
        	.delete("/v1/championships/" + championship2.id().toString())
		      .headers(getHeaders(adminToken)));
        this.mockMvc.perform(MockMvcRequestBuilders
        	.delete("/v1/championships/" + championship.id().toString())
	        .headers(getHeaders(adminToken)));
        
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/championships")
    		.contentType(MediaType.APPLICATION_JSON)
    		.accept(MediaType.APPLICATION_JSON)
	        .headers(getHeaders(userToken)))
    		.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        logoutAdmin(adminToken);
        logoutUser(userToken);
   }
}

