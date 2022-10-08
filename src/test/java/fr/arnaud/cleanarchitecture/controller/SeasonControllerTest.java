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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.arnaud.cleanarchitecture.CleanArchitectureApplication;
import fr.arnaud.cleanarchitecture.core.entities.Season;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CleanArchitectureApplication.class)
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
public class SeasonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();
    
    @Test
    public void createDeleteSeason() throws Exception {
    	
    	//create one season
        UUID uuid = UUID.randomUUID();
    	Season season = Season.builder().id(uuid).name("2022/2023").startDate(LocalDateTime.of(2022, 9, 1, 0, 0, 0)).endDate(LocalDateTime.of(2023, 6, 30, 0, 0, 0)).build();
        String json = this.mapper.writeValueAsString(season);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/seasons")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());

        //check if season was correcty created
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/seasons/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("{\"id\":\"" + uuid.toString()  + "\",\"name\":\"2022/2023\",\"startDate\":\"2022-09-01T00:00:00\",\"endDate\":\"2023-06-30T00:00:00\"}"));
        
         //delete the season
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/seasons/" + uuid.toString()));

        //verify that the season is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/seasons/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	season = Season.builder().id(uuid).name("2022/2023").startDate(LocalDateTime.of(2022, 9, 1, 0, 0, 0)).endDate(LocalDateTime.of(2023, 6, 30, 0, 0, 0)).build();
        
        json = this.mapper.writeValueAsString(season);
 
    	//create 2 seasons
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/seasons")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        
    	uuid = UUID.randomUUID();
    	season = Season.builder().id(uuid).name("2022/2023").startDate(LocalDateTime.of(2023, 9, 1, 0, 0)).endDate(LocalDateTime.of(2024, 6, 30, 0, 0)).build();
        
        json = this.mapper.writeValueAsString(season);
 
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/seasons")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        
        //check if getSeasons return 2 seasons
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/seasons")
        		.content(json)
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

