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
import fr.arnaud.cleanarchitecture.infrastructure.delivery.model.v1.Championship;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.model.v1.League;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.model.v1.Match;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.model.v1.Player;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.model.v1.Season;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.model.v1.Team;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CleanArchitectureApplication.class)
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
public class MatchControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .serializers(LOCAL_DATETIME_SERIALIZER)
            .serializationInclusion(JsonInclude.Include.NON_NULL).build();
    
    @Test
    public void createDeleteMatch() throws Exception {
    	
    	//create one 
    	UUID uuid = UUID.randomUUID();
    	Season season = new Season(uuid, "2022/2023", LocalDateTime.of(2022, 9, 1, 0, 0, 0), LocalDateTime.of(2023, 6, 30, 0, 0, 0));
    	String json = this.mapper.writeValueAsString(season);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/seasons")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());
        
    	uuid = UUID.randomUUID();
    	Player player = new Player(uuid, "arnaud", "barbe");
    	json = this.mapper.writeValueAsString(player);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/players")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());

    	uuid = UUID.randomUUID();
    	League league = new League(uuid, "Afebas");
    	uuid = UUID.randomUUID();
    	json = this.mapper.writeValueAsString(league);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/leagues")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());
        
    	Championship championship = new Championship(uuid, "France", player, season, league);
    	
        json = this.mapper.writeValueAsString(championship);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/championships")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());
    	
        uuid = UUID.randomUUID();
    	Team team1 = new Team(uuid, "Poule o pot", championship);
        json = this.mapper.writeValueAsString(team1);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/teams")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());

    	Team team2 = new Team(uuid, "Swimming Poule", championship);
        json = this.mapper.writeValueAsString(team2);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/teams")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());

        uuid = UUID.randomUUID();
    	Match match = new Match(uuid, LocalDateTime.now(), championship, team1, team2, 10, 2);
        json = this.mapper.writeValueAsString(match);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/matchs")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());

        //check if match was correcty created
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/matchs/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk());


        //delete the match
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/matchs/" + uuid.toString()));

        //verify that the match is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/matchs/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	Match aller = new Match(uuid, LocalDateTime.now(), championship, team1, team2, 10, 2);
        
        json = this.mapper.writeValueAsString(aller);
 
    	//create 2 matchs
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/matchs")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        
    	uuid = UUID.randomUUID();
    	Match retour = new Match(uuid, LocalDateTime.now(), championship, team2, team1, 4, 8);
        
        json = this.mapper.writeValueAsString(retour);
 
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/matchs")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        
        //check if getMatchs return 2 matchs
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/matchs")
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

        //delete the match
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/matchs/" + aller.id().toString()));
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/matchs/" + retour.id().toString()));
    }
}

