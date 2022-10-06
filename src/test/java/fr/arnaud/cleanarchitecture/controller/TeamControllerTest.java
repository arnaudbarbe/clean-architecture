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

    @MockBean
    private TeamService teamService;

    private ObjectMapper mapper = new ObjectMapper();
    
    @Test
    public void createTeam() throws Exception {
    	
    	UUID uuid = UUID.randomUUID();
    	Team team = Team.builder().id(uuid).name("Poule").build();
        
        String json = this.mapper.writeValueAsString(team);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/teams")
        		.content(json)
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void deleteTeam() throws Exception {
    	
        UUID uuid = UUID.randomUUID();
    	Team team = Team.builder().id(uuid).name("Poule").build();
        
        String json = this.mapper.writeValueAsString(team);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/teams")
        		.content(json)
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON));


        this.mockMvc.perform(MockMvcRequestBuilders.delete("/teams/" + uuid.toString()));


        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get("/teams/" + uuid.toString()));

         
        String json = result.getResponse().getContentAsString();

        Team existingTeam = mapper.readValue(json, Team.class);

     
        Assert.assertNull(existingTeam);
    }


    @Test
    public void createTeam() throws Exception {
    	
    	UUID uuid = UUID.randomUUID();
    	Team team = Team.builder().id(uuid).name("Poule").build();
        
        String json = this.mapper.writeValueAsString(team);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/teams")
        		.content(json)
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}

