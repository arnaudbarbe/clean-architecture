package fr.arnaud.cleanarchitecture.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.arnaud.cleanarchitecture.AbstractTest;
import fr.arnaud.cleanarchitecture.CleanArchitectureApplication;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;




@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CleanArchitectureApplication.class)
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
public class LeagueControllerTest extends AbstractTest {

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .serializers(LOCAL_DATETIME_SERIALIZER)
            .serializationInclusion(JsonInclude.Include.NON_NULL).build();
    
    @Test
    public void crudLeague() throws Exception {
    	
        //delete unknown object
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/leagues/" + UUID.randomUUID().toString()));

        //create one league
        UUID uuid = UUID.randomUUID();
    	LeagueDto league = new LeagueDto(uuid, "Afebas");
        String json = this.mapper.writeValueAsString(league);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/leagues")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());

        //check if league was correctly created
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/leagues/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("{\"id\":\"" + uuid.toString()  + "\",\"name\":\"Afebas\"}"));

        //update the league
        league = new LeagueDto(uuid, "FFB");
        
        json = this.mapper.writeValueAsString(league);
        
        this.mockMvc.perform(MockMvcRequestBuilders.put("/v1/leagues/" + uuid.toString())
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isNoContent());
        
        //check if championship was correctly updated
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/leagues/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(json));
       
        //delete the league
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/leagues/" + uuid.toString()));

        //verify that the league is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/leagues/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	LeagueDto league1 = new LeagueDto(uuid, "Afebas");
        
        json = this.mapper.writeValueAsString(league1);
 
    	//create 2 leagues
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/leagues")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        
    	uuid = UUID.randomUUID();
    	LeagueDto league2 = new LeagueDto(uuid, "FBEP");
        
        json = this.mapper.writeValueAsString(league2);
 
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/leagues")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        
        //check if getLeagues return 2 leagues
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/leagues")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/leagues/" + league1.id().toString()));
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/leagues/" + league2.id().toString()));
        
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/leagues")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}

