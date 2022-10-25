package fr.arnaud.cleanarchitecture.event;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.arnaud.cleanarchitecture.AbstractTest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;




public class LeagueEventTest extends AbstractTest {

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .serializers(LOCAL_DATETIME_SERIALIZER)
            .serializationInclusion(JsonInclude.Include.NON_NULL).build();
    
    @Test
    public void crudLeague() throws Exception {
    	
        //delete unknown object
        this.leaguePublisher.deleteLeague(UUID.randomUUID());
        
        //create one league
        UUID uuid = UUID.randomUUID();
    	LeagueDto league = new LeagueDto(uuid, "Afebas");
        String json = this.mapper.writeValueAsString(league);
        
        this.leaguePublisher.createLeague(league);
        Thread.sleep(2000);
        
       //check if league was correctly created
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/leagues/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("{\"id\":\"" + uuid.toString()  + "\",\"name\":\"Afebas\"}"));

        //update the league
        league = new LeagueDto(uuid, "FFB");
        
        json = this.mapper.writeValueAsString(league);
        
        this.leaguePublisher.updateLeague(league);
        Thread.sleep(2000);
        
        //check if championship was correctly updated
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/leagues/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(json));
       
        //delete the league
        this.leaguePublisher.deleteLeague(uuid);
        Thread.sleep(2000);
        
        //verify that the league is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/leagues/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	LeagueDto league1 = new LeagueDto(uuid, "Afebas");
        
        json = this.mapper.writeValueAsString(league1);
 
    	//create 2 leagues
        this.leaguePublisher.createLeague(league1);
        
    	uuid = UUID.randomUUID();
    	LeagueDto league2 = new LeagueDto(uuid, "FBEP");
        
        json = this.mapper.writeValueAsString(league2);
 
        this.leaguePublisher.createLeague(league2);
        Thread.sleep(2000);
        
        //check if getLeagues return 2 leagues
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/leagues")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
        this.leaguePublisher.deleteLeague(league1.id());
        this.leaguePublisher.deleteLeague(league2.id());
        Thread.sleep(2000);
        
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/leagues")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}

