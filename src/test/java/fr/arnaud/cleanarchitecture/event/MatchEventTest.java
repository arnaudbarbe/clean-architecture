package fr.arnaud.cleanarchitecture.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.ChampionshipDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.MatchDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.PlayerDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.SeasonDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.TeamDto;




public class MatchEventTest extends AbstractTest {

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .serializers(LOCAL_DATETIME_SERIALIZER)
            .serializationInclusion(JsonInclude.Include.NON_NULL).build();
    
    @Test
    public void crudMatch() throws Exception {
    	
        //delete unknown object
        this.matchPublisher.deleteMatch(UUID.randomUUID());
        Thread.sleep(2000);

    	UUID uuid = UUID.randomUUID();
    	SeasonDto season = new SeasonDto(uuid, "2022/2023", LocalDateTime.of(2022, 9, 1, 0, 0, 0, 0), LocalDateTime.of(2023, 6, 30, 0, 0, 0, 0));
        this.seasonPublisher.createSeason(season);
        Thread.sleep(2000);

    	uuid = UUID.randomUUID();
    	PlayerDto player = new PlayerDto(uuid, "arnaud", "barbe");
        this.playerPublisher.createPlayer(player);
        Thread.sleep(2000);

    	uuid = UUID.randomUUID();
    	LeagueDto league = new LeagueDto(uuid, "Afebas");
    	this.leaguePublisher.createLeague(league);
        Thread.sleep(2000);

    	ChampionshipDto championship = new ChampionshipDto(uuid, "France", player, season, league);
        this.championshipPublisher.createChampionship(championship);
        Thread.sleep(2000);

        uuid = UUID.randomUUID();
    	TeamDto team1 = new TeamDto(uuid, "Poule o pot", championship);
         
        this.teamPublisher.createTeam(team1);
        Thread.sleep(2000);

        uuid = UUID.randomUUID();
    	TeamDto team2 = new TeamDto(uuid, "Swimming Poule", championship);
        
    	this.teamPublisher.createTeam(team2);
        Thread.sleep(2000);

        //create one match
        uuid = UUID.randomUUID();
    	MatchDto match = new MatchDto(uuid, LocalDateTime.now().withNano(0), championship, team1, team2, 10, 2);
        String json = this.mapper.writeValueAsString(match);
        
        this.matchPublisher.createMatch(match);
        Thread.sleep(2000);
        
       //check if match was correctly created
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/matchs/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        
        assertEquals(match, this.mapper.readValue(contentAsString, MatchDto.class));
 
        //update the match
        match = new MatchDto(uuid, LocalDateTime.now().withNano(0), championship, team1, team2, 10, 2);
        
        json = this.mapper.writeValueAsString(match);
        
        this.matchPublisher.updateMatch(match);
        Thread.sleep(2000);
        
        //check if championship was correctly updated
        resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/matchs/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk());
       
        result = resultActions.andReturn();
        contentAsString = result.getResponse().getContentAsString();
        
        assertEquals(match, this.mapper.readValue(contentAsString, MatchDto.class));

        //delete the match
        this.matchPublisher.deleteMatch(uuid);
        Thread.sleep(2000);
        
        //verify that the match is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/matchs/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	MatchDto match1 = new MatchDto(uuid, LocalDateTime.now().withNano(0), championship, team1, team2, 10, 2);
        
        json = this.mapper.writeValueAsString(match1);
 
    	//create 2 matchs
        this.matchPublisher.createMatch(match1);
        Thread.sleep(2000);

    	uuid = UUID.randomUUID();
    	MatchDto match2 = new MatchDto(uuid, LocalDateTime.now().withNano(0), championship, team2, team1, 4, 8);
        
        json = this.mapper.writeValueAsString(match2);
 
        this.matchPublisher.createMatch(match2);
        Thread.sleep(2000);
        
        //check if getMatchs return 2 matchs
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/matchs")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
        this.matchPublisher.deleteMatch(match1.id());
        this.matchPublisher.deleteMatch(match2.id());
        Thread.sleep(2000);
        
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/matchs")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}

