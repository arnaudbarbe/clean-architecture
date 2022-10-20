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
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.SeasonDto;


public class SeasonControllerTest extends AbstractTest {

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .serializers(LOCAL_DATETIME_SERIALIZER)
            .serializationInclusion(JsonInclude.Include.NON_NULL).build();
    
    @Test
    public void crudSeason() throws Exception {
    	
    	//create one season
        UUID uuid = UUID.randomUUID();
    	SeasonDto season = new SeasonDto(uuid, "2022/2023", LocalDateTime.of(2022, 9, 1, 0, 0, 0), LocalDateTime.of(2023, 6, 30, 0, 0, 0));
        String json = this.mapper.writeValueAsString(season);
        
        this.seasonPublisherService.createSeasonAsync(season);
        Thread.sleep(2000);

        //check if season was correcty created
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/seasons/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(json));

        //delete the season
        this.seasonPublisherService.deleteLeagueAsync(uuid);
        Thread.sleep(2000);

        //verify that the player is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/seasons/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	SeasonDto season1 = new SeasonDto(uuid, "2022/2023", LocalDateTime.of(2022, 9, 1, 0, 0, 0), LocalDateTime.of(2023, 6, 30, 0, 0, 0));
        
        json = this.mapper.writeValueAsString(season1);
 
    	//create 2 seasons
        this.seasonPublisherService.createSeasonAsync(season1);
        
    	uuid = UUID.randomUUID();
    	SeasonDto season2 = new SeasonDto(uuid, "2023/2024", LocalDateTime.of(2023, 9, 1, 0, 0, 0), LocalDateTime.of(2024, 6, 30, 0, 0, 0));
        
        json = this.mapper.writeValueAsString(season2);
 
        this.seasonPublisherService.createSeasonAsync(season2);
        Thread.sleep(2000);
        //check if getPlayers return 2 seasons
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/seasons")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
        this.seasonPublisherService.deleteLeagueAsync(season1.id());
        this.seasonPublisherService.deleteLeagueAsync(season2.id());

    }
}

