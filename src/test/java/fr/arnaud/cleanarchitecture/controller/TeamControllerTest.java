package fr.arnaud.cleanarchitecture.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.arnaud.cleanarchitecture.CleanArchitectureApplication;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.ChampionshipDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.PlayerDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.SeasonDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.TeamDto;




@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CleanArchitectureApplication.class)
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
public class TeamControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .serializers(LOCAL_DATETIME_SERIALIZER)
            .serializationInclusion(JsonInclude.Include.NON_NULL).build();
    
    @Test
    public void createDeleteTeam() throws Exception {
    	
    	//create one team
    	UUID uuid = UUID.randomUUID();
    	SeasonDto season = new SeasonDto(uuid, "2022/2023", LocalDateTime.of(2022, 9, 1, 0, 0, 0), LocalDateTime.of(2023, 6, 30, 0, 0, 0));
    	String json = this.mapper.writeValueAsString(season);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/seasons")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());
        
    	uuid = UUID.randomUUID();
    	PlayerDto player = new PlayerDto(uuid, "arnaud", "barbe");
    	json = this.mapper.writeValueAsString(player);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/players")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());

    	uuid = UUID.randomUUID();
    	LeagueDto league = new LeagueDto(uuid, "Afebas");
    	uuid = UUID.randomUUID();
    	json = this.mapper.writeValueAsString(league);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/leagues")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());
        
    	ChampionshipDto championship = new ChampionshipDto(uuid, "France", player, season, league);
    	
        json = this.mapper.writeValueAsString(championship);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/championships")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());
    	
        uuid = UUID.randomUUID();
    	TeamDto team = new TeamDto(uuid, "Poule", championship);
        json = this.mapper.writeValueAsString(team);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/teams")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());

        //check if team was correcty created
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/teams/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("{\"id\":\"" + uuid.toString()  + "\",\"name\":\"Poule\"}"));

        //delete the team
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/teams/" + uuid.toString()));
        

        //verify that the team is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/teams/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	TeamDto team1 = new TeamDto(uuid, "Poule1", championship);
        
        json = this.mapper.writeValueAsString(team1);
 
    	//create 2 teams
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/teams")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        
    	uuid = UUID.randomUUID();
    	TeamDto team2 = new TeamDto(uuid, "Poule2", championship);
        
        json = this.mapper.writeValueAsString(team2);
 
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/teams")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        
        //check if getTeams return 2 teams
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/teams")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/teams/" + team1.id().toString()));
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/teams/" + team2.id().toString()));
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/championships/" + championship.id().toString()));
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/players/" + player.id().toString()));
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/seasons/" + season.id().toString()));
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/leagues/" + league.id().toString()));

    }
}

