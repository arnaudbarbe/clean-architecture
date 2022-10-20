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




public class ChampionshipEventTest extends AbstractTest {

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .serializers(LOCAL_DATETIME_SERIALIZER)
            .serializationInclusion(JsonInclude.Include.NON_NULL).build();
    
    @Test
    public void crudChampionship() throws Exception {
    	
        //delete unknown object
        this.championshipPublisherService.deleteChampionshipAsync(UUID.randomUUID());
        
        UUID uuid = UUID.randomUUID();
    	LeagueDto league = new LeagueDto(uuid, "Afebas");
        this.leaguePublisherService.createLeagueAsync(league);

        uuid = UUID.randomUUID();
    	PlayerDto player = new PlayerDto(uuid, "arnaud", "barbe");
        this.playerPublisherService.createPlayerAsync(player);
        
        uuid = UUID.randomUUID();
    	SeasonDto season = new SeasonDto(uuid, "2022/2023", LocalDateTime.of(2022, 9, 1, 0, 0, 0), LocalDateTime.of(2023, 6, 30, 0, 0, 0));
        this.seasonPublisherService.createSeasonAsync(season);
        
        //create one championship
        uuid = UUID.randomUUID();
    	ChampionshipDto championship = new ChampionshipDto(uuid, "France", player, season, league);
        String json = this.mapper.writeValueAsString(championship);
        
        this.championshipPublisherService.createChampionshipAsync(championship);
        Thread.sleep(2000);
        
       //check if championship was correctly created
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/championships/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(json));

        //update the championship
        championship = new ChampionshipDto(uuid, "USA", player, season, league);
        
        json = this.mapper.writeValueAsString(championship);
        
        this.championshipPublisherService.updateChampionshipAsync(championship);
        Thread.sleep(2000);
        
        //check if championship was correctly updated
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/championships/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(json));
       
        //delete the championship
        this.championshipPublisherService.deleteChampionshipAsync(uuid);
        Thread.sleep(2000);
        
        //verify that the championship is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/championships/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	ChampionshipDto championship1 = new ChampionshipDto(uuid, "France", player, season, league);
        
        json = this.mapper.writeValueAsString(championship1);
 
    	//create 2 championships
        this.championshipPublisherService.createChampionshipAsync(championship1);
        
    	uuid = UUID.randomUUID();
    	ChampionshipDto championship2 = new ChampionshipDto(uuid, "Belgium", player, season, league);
        
        json = this.mapper.writeValueAsString(championship2);
 
        this.championshipPublisherService.createChampionshipAsync(championship2);
        Thread.sleep(2000);
        
        //check if getChampionships return 2 championships
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/championships")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
        this.championshipPublisherService.deleteChampionshipAsync(championship1.id());
        this.championshipPublisherService.deleteChampionshipAsync(championship2.id());
        Thread.sleep(2000);
        
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/championships")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}

