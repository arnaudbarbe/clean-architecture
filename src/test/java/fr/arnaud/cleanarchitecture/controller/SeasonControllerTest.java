package fr.arnaud.cleanarchitecture.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.arnaud.cleanarchitecture.AbstractTest;
import fr.arnaud.cleanarchitecture.CleanArchitectureApplication;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.SeasonDto;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CleanArchitectureApplication.class)
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
public class SeasonControllerTest extends AbstractTest {

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .serializers(LOCAL_DATETIME_SERIALIZER)
            .serializationInclusion(JsonInclude.Include.NON_NULL).build();
    
    @Test
    public void crudSeason() throws Exception {
    	
        //delete unknown object
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/seasons/" + UUID.randomUUID().toString()));

    	//create one season
        UUID uuid = UUID.randomUUID();
    	SeasonDto season = new SeasonDto(uuid, "2022/2023", LocalDateTime.of(2022, 9, 1, 0, 0, 0), LocalDateTime.of(2023, 6, 30, 0, 0, 0));
        String json = this.mapper.writeValueAsString(season);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/seasons")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());

        //check if season was correcty created
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/seasons/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(json));
        
        //update the season
        season = new SeasonDto(uuid, "2021/2022", LocalDateTime.of(2022, 9, 1, 0, 0, 0), LocalDateTime.of(2023, 6, 30, 0, 0, 0));
        
        json = this.mapper.writeValueAsString(season);
        
        this.mockMvc.perform(MockMvcRequestBuilders.put("/v1/seasons/" + uuid.toString())
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isNoContent());
        
        //check if season was correctly updated
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/seasons/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(json));

         //delete the season
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/seasons/" + uuid.toString()));

        //verify that the season is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/seasons/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	SeasonDto season1 = new SeasonDto(uuid, "2022/2023", LocalDateTime.of(2022, 9, 1, 0, 0, 0), LocalDateTime.of(2023, 6, 30, 0, 0, 0));
        
        json = this.mapper.writeValueAsString(season1);
 
    	//create 2 seasons
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/seasons")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        
    	uuid = UUID.randomUUID();
    	SeasonDto season2 = new SeasonDto(uuid, "2023/2024", LocalDateTime.of(2023, 9, 1, 0, 0, 0), LocalDateTime.of(2024, 6, 30, 0, 0, 0));
        
        json = this.mapper.writeValueAsString(season2);
 
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/seasons")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        
        //check if getSeasons return 2 seasons
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/seasons")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/seasons/" + season1.id().toString()));
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/seasons/" + season2.id().toString()));
        
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/seasons")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}

