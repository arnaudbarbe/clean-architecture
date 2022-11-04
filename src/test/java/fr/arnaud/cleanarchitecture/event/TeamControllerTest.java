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
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.TokenDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.ChampionshipDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.PlayerDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.SeasonDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.TeamDto;




public class TeamControllerTest extends AbstractTest {

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .serializers(LOCAL_DATETIME_SERIALIZER)
            .serializationInclusion(JsonInclude.Include.NON_NULL).build();
    
    @Test
    public void crudTeam() throws Exception {
    	
    	TokenDto userToken = loginUser();

        //delete unknown object
    	this.teamPublisher.deleteTeam(UUID.randomUUID());

    	//create one team
    	UUID uuid = UUID.randomUUID();
    	SeasonDto season = new SeasonDto(uuid, "2022/2023", LocalDateTime.of(2022, 9, 1, 0, 0, 0, 0), LocalDateTime.of(2023, 6, 30, 0, 0, 0, 0));
    	String json = this.mapper.writeValueAsString(season);
    	this.seasonPublisher.createSeason(season);
        Thread.sleep(2000);
        
    	uuid = UUID.randomUUID();
    	PlayerDto player = new PlayerDto(uuid, "arnaud", "barbe");
    	json = this.mapper.writeValueAsString(player);
    	this.playerPublisher.createPlayer(player);
        Thread.sleep(2000);

    	uuid = UUID.randomUUID();
    	LeagueDto league = new LeagueDto(uuid, "Afebas");
    	uuid = UUID.randomUUID();
    	json = this.mapper.writeValueAsString(league);
    	this.leaguePublisher.createLeague(league);
        Thread.sleep(2000);
        
    	ChampionshipDto championship = new ChampionshipDto(uuid, "France", player, season, league);
    	
        json = this.mapper.writeValueAsString(championship);
        
        this.championshipPublisher.createChampionship(championship);
        Thread.sleep(2000);
   	
        uuid = UUID.randomUUID();
    	TeamDto team = new TeamDto(uuid, "Poule", championship);
        json = this.mapper.writeValueAsString(team);
        
        this.teamPublisher.createTeam(team);
        Thread.sleep(2000);

        //check if team was correctly created
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/teams/" + uuid.toString())
        	.headers(getHeaders(userToken)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        
        assertEquals(team, this.mapper.readValue(contentAsString, TeamDto.class));
        
        //update the team
        team = new TeamDto(uuid, "PouPoule", championship);
        
        json = this.mapper.writeValueAsString(team);
        
        this.teamPublisher.updateTeam(team);
        Thread.sleep(2000);
        
        //check if team was correctly updated
        resultActions = this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/teams/" + uuid.toString())
        	.headers(getHeaders(userToken)))
            .andExpect(MockMvcResultMatchers.status().isOk());
        
        result = resultActions.andReturn();
        contentAsString = result.getResponse().getContentAsString();
        
        assertEquals(team, this.mapper.readValue(contentAsString, TeamDto.class));
 
        //delete the team
        this.teamPublisher.deleteTeam(uuid);        
        Thread.sleep(2000);

        //verify that the team is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/teams/" + uuid.toString())
        	.headers(getHeaders(userToken)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	TeamDto team1 = new TeamDto(uuid, "Poule1", championship);
        
        json = this.mapper.writeValueAsString(team1);
 
    	//create 2 teams
        this.teamPublisher.createTeam(team1);
        Thread.sleep(2000);
        
    	uuid = UUID.randomUUID();
    	TeamDto team2 = new TeamDto(uuid, "Poule2", championship);
        
        json = this.mapper.writeValueAsString(team2);
 
        this.teamPublisher.createTeam(team2);
        Thread.sleep(2000);
       
        //check if getTeams return 2 teams
        this.mockMvc.perform(MockMvcRequestBuilders
    		.get("/v1/teams")
    		.contentType(MediaType.APPLICATION_JSON)
    		.accept(MediaType.APPLICATION_JSON)
    		.headers(getHeaders(userToken)))
    		.andExpect(jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.status().isOk());
        
        this.teamPublisher.deleteTeam(team1.id());
        this.teamPublisher.deleteTeam(team2.id());
        Thread.sleep(2000);

        this.mockMvc.perform(MockMvcRequestBuilders
    		.get("/v1/teams")
    		.contentType(MediaType.APPLICATION_JSON)
    		.accept(MediaType.APPLICATION_JSON)
    		.headers(getHeaders(userToken)))
    		.andExpect(jsonPath("$", Matchers.hasSize(0)))
            .andExpect(MockMvcResultMatchers.status().isOk());

    }
}

