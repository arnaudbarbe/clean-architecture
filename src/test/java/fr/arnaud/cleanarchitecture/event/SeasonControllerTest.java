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
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.SeasonDto;


public class SeasonControllerTest extends AbstractTest {

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .serializers(LOCAL_DATETIME_SERIALIZER)
            .serializationInclusion(JsonInclude.Include.NON_NULL).build();
    
    @Test
    public void crudSeason() throws Exception {
    	
    	TokenDto userToken = loginUser();

    	//create one season
        UUID uuid = UUID.randomUUID();
    	SeasonDto season = new SeasonDto(uuid, "2022/2023", LocalDateTime.of(2022, 9, 1, 0, 0, 0).withNano(0), LocalDateTime.of(2023, 6, 30, 0, 0, 0).withNano(0));
        String json = this.mapper.writeValueAsString(season);
        
        this.seasonPublisher.createSeason(season);
        Thread.sleep(2000);

        //check if season was correcty created
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/seasons/" + uuid.toString())
        	.headers(getHeaders(userToken)))
            .andExpect(MockMvcResultMatchers.status().isOk());
        
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        
        assertEquals(season, this.mapper.readValue(contentAsString, SeasonDto.class));


        season = new SeasonDto(uuid, "2025/2026", LocalDateTime.of(2025, 9, 1, 0, 0, 0).withNano(0), LocalDateTime.of(2026, 6, 30, 0, 0, 0).withNano(0));
        json = this.mapper.writeValueAsString(season);
        
        this.seasonPublisher.updateSeason(season);
        Thread.sleep(2000);
       
        resultActions = this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/seasons/" + uuid.toString())
        	.headers(getHeaders(userToken)))
        	.andExpect(MockMvcResultMatchers.status().isOk());

        result = resultActions.andReturn();
        contentAsString = result.getResponse().getContentAsString();
        
        assertEquals(season, this.mapper.readValue(contentAsString, SeasonDto.class));

        //delete the season
        this.seasonPublisher.deleteLeague(uuid);
        Thread.sleep(2000);

        //verify that the player is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/seasons/" + uuid.toString())
        	.headers(getHeaders(userToken)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	SeasonDto season1 = new SeasonDto(uuid, "2022/2023", LocalDateTime.of(2022, 9, 1, 0, 0, 0).withNano(0), LocalDateTime.of(2023, 6, 30, 0, 0, 0).withNano(0));
        
        json = this.mapper.writeValueAsString(season1);
 
    	//create 2 seasons
        this.seasonPublisher.createSeason(season1);
        
    	uuid = UUID.randomUUID();
    	SeasonDto season2 = new SeasonDto(uuid, "2023/2024", LocalDateTime.of(2023, 9, 1, 0, 0, 0).withNano(0), LocalDateTime.of(2024, 6, 30, 0, 0, 0).withNano(0));
        
        json = this.mapper.writeValueAsString(season2);
 
        this.seasonPublisher.createSeason(season2);
        Thread.sleep(2000);
        //check if getPlayers return 2 seasons
        this.mockMvc.perform(MockMvcRequestBuilders
    		.get("/v1/seasons")
    		.contentType(MediaType.APPLICATION_JSON)
    		.accept(MediaType.APPLICATION_JSON)
    		.headers(getHeaders(userToken)))
    		.andExpect(jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.status().isOk());
        
        this.seasonPublisher.deleteLeague(season1.id());
        this.seasonPublisher.deleteLeague(season2.id());

    }
}

