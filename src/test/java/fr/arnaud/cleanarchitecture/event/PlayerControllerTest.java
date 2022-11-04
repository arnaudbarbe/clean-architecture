package fr.arnaud.cleanarchitecture.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.PlayerDto;


public class PlayerControllerTest extends AbstractTest {

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .serializers(LOCAL_DATETIME_SERIALIZER)
            .serializationInclusion(JsonInclude.Include.NON_NULL).build();
    
    @Test
    public void crudPlayer() throws Exception {
    	
    	TokenDto userToken = loginUser();

    	//create one player
        UUID uuid = UUID.randomUUID();
    	PlayerDto player = new PlayerDto(uuid, "arnaud", "barbe");
        String json = this.mapper.writeValueAsString(player);
        
        this.playerPublisher.createPlayer(player);
        Thread.sleep(2000);

        //check if player was correctly created
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/players/" + uuid.toString())
        	.headers(getHeaders(userToken)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        
        assertEquals(player, this.mapper.readValue(contentAsString, PlayerDto.class));

        
        player = new PlayerDto(uuid, "arnaud", "barbe2");
        
        json = this.mapper.writeValueAsString(player);
        
        this.playerPublisher.updatePlayer(player);
        Thread.sleep(2000);
       
        resultActions = this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/players/" + uuid.toString())
        	.headers(getHeaders(userToken)))
        	.andExpect(MockMvcResultMatchers.status().isOk());
        
        result = resultActions.andReturn();
        contentAsString = result.getResponse().getContentAsString();
        
        assertEquals(player, this.mapper.readValue(contentAsString, PlayerDto.class));

        //delete the player
        this.playerPublisher.deletePlayer(uuid);
        Thread.sleep(2000);

        //verify that the player is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders
        	.get("/v1/players/" + uuid.toString())
        	.headers(getHeaders(userToken)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	PlayerDto player1 = new PlayerDto(uuid, "arnaud", "barbe");
        
        json = this.mapper.writeValueAsString(player1);
 
    	//create 2 players
        this.playerPublisher.createPlayer(player1);
        
    	uuid = UUID.randomUUID();
    	PlayerDto player2 = new PlayerDto(uuid, "christophe", "lambert");
        
        json = this.mapper.writeValueAsString(player2);
 
        this.playerPublisher.createPlayer(player2);
        Thread.sleep(2000);
        //check if getPlayers return 2 players
        this.mockMvc.perform(MockMvcRequestBuilders
        		.get("/v1/players")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON)
        		.headers(getHeaders(userToken)))
        		.andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
        this.playerPublisher.deletePlayer(player1.id());
        this.playerPublisher.deletePlayer(player2.id());

    }
}

