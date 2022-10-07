package fr.arnaud.cleanarchitecture.controller;

import java.util.UUID;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.arnaud.cleanarchitecture.core.model.Team;
import fr.arnaud.cleanarchitecture.core.service.team.TeamService;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();
    
    @Test
    public void createDeleteTeam() throws Exception {
    	
        UUID uuid = UUID.randomUUID();
    	Team team = Team.builder().id(uuid).name("Poule").build();
        
        String json = this.mapper.writeValueAsString(team);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/V1/teams")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/V1/teams/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("{\"id\":" + uuid.toString()  + ", \"name\":\"Poule\"}"));

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/V1/teams/" + uuid.toString()));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/V1/teams/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("{\"id\":" + uuid.toString()  + ", \"name\":\"Poule\"}"));

    }


    @Test
    public void getTeams() throws Exception {
    	
    	UUID uuid = UUID.randomUUID();
    	Team team = Team.builder().id(uuid).name("Poule").build();
        
        String json = this.mapper.writeValueAsString(team);
        
        this.mockMvc.perform(MockMvcRequestBuilders.get("/V1/teams")
        		.content(json)
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}

