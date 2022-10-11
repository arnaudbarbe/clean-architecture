package fr.arnaud.cleanarchitecture.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.PlayerDto;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CleanArchitectureApplication.class)
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
public class PlayerControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .serializers(LOCAL_DATETIME_SERIALIZER)
            .serializationInclusion(JsonInclude.Include.NON_NULL).build();
    
    @Test
    public void createDeletePlayer() throws Exception {
    	
    	//create one player
        UUID uuid = UUID.randomUUID();
    	PlayerDto player = new PlayerDto(uuid, "arnaud", "barbe");
        String json = this.mapper.writeValueAsString(player);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/players")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());

        //check if player was correcty created
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/players/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("{\"id\":\"" + uuid.toString()  + "\",\"firstName\":\"arnaud\",\"lastName\":\"barbe\"}"));

        //delete the player
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/players/" + uuid.toString()));

        //verify that the player is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/players/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	PlayerDto player1 = new PlayerDto(uuid, "arnaud", "barbe");
        
        json = this.mapper.writeValueAsString(player1);
 
    	//create 2 players
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/players")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        
    	uuid = UUID.randomUUID();
    	PlayerDto player2 = new PlayerDto(uuid, "christophe", "lambert");
        
        json = this.mapper.writeValueAsString(player2);
 
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/players")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        
        //check if getPlayers return 2 players
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/players")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/players/" + player1.id().toString()));
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/players/" + player2.id().toString()));

    }
}

