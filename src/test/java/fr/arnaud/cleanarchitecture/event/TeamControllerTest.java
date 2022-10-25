package fr.arnaud.cleanarchitecture.event;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
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
    	
        //delete unknown object
    	this.teamPublisher.deleteTeamAsync(UUID.randomUUID());

    	//create one team
    	UUID uuid = UUID.randomUUID();
    	SeasonDto season = new SeasonDto(uuid, "2022/2023", LocalDateTime.of(2022, 9, 1, 0, 0, 0), LocalDateTime.of(2023, 6, 30, 0, 0, 0));
    	String json = this.mapper.writeValueAsString(season);
    	this.seasonPublisher.createSeasonAsync(season);
        Thread.sleep(2000);
        
    	uuid = UUID.randomUUID();
    	PlayerDto player = new PlayerDto(uuid, "arnaud", "barbe");
    	json = this.mapper.writeValueAsString(player);
    	this.playerPublisher.createPlayerAsync(player);
        Thread.sleep(2000);

    	uuid = UUID.randomUUID();
    	LeagueDto league = new LeagueDto(uuid, "Afebas");
    	uuid = UUID.randomUUID();
    	json = this.mapper.writeValueAsString(league);
    	this.leaguePublisher.createLeagueAsync(league);
        Thread.sleep(2000);
        
    	ChampionshipDto championship = new ChampionshipDto(uuid, "France", player, season, league);
    	
        json = this.mapper.writeValueAsString(championship);
        
        this.championshipPublisher.createChampionshipAsync(championship);
        Thread.sleep(2000);
   	
        uuid = UUID.randomUUID();
    	TeamDto team = new TeamDto(uuid, "Poule", championship);
        json = this.mapper.writeValueAsString(team);
        
        this.teamPublisher.createTeamAsync(team);
        Thread.sleep(2000);

        //check if team was correctly created
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/teams/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(json));

        //update the team
        team = new TeamDto(uuid, "PouPoule", championship);
        
        json = this.mapper.writeValueAsString(team);
        
        this.teamPublisher.updateTeamAsync(team);
        Thread.sleep(2000);
        
        //check if team was correctly updated
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/teams/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(json));

        //delete the team
        this.teamPublisher.deleteTeamAsync(uuid);        
        Thread.sleep(2000);

        //verify that the team is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/teams/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	TeamDto team1 = new TeamDto(uuid, "Poule1", championship);
        
        json = this.mapper.writeValueAsString(team1);
 
    	//create 2 teams
        this.teamPublisher.createTeamAsync(team1);
        Thread.sleep(2000);
        
    	uuid = UUID.randomUUID();
    	TeamDto team2 = new TeamDto(uuid, "Poule2", championship);
        
        json = this.mapper.writeValueAsString(team2);
 
        this.teamPublisher.createTeamAsync(team2);
        Thread.sleep(2000);
       
        //check if getTeams return 2 teams
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/teams")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
        this.teamPublisher.deleteTeamAsync(team1.id());
        this.teamPublisher.deleteTeamAsync(team2.id());
        Thread.sleep(2000);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/teams")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}

