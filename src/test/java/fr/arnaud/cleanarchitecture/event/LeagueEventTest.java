package fr.arnaud.cleanarchitecture.event;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;




@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CleanArchitectureApplication.class)
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
public class LeagueEventTest extends AbstractTest {

    private ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .serializers(LOCAL_DATETIME_SERIALIZER)
            .serializationInclusion(JsonInclude.Include.NON_NULL).build();
    
    @Test
    public void createDeleteLeague() throws Exception {
    	
    	//create one league
        UUID uuid = UUID.randomUUID();
    	LeagueDto league = new LeagueDto(uuid, "Afebas");
        String json = this.mapper.writeValueAsString(league);
        
        this.leaguePublisherService.createLeagueAsync(league);
        Thread.sleep(2000);

        //check if league was correcty created
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/leagues/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("{\"id\":\"" + uuid.toString()  + "\",\"name\":\"Afebas\"}"));

        //delete the league
        this.leaguePublisherService.deleteLeagueAsync(uuid);
        Thread.sleep(2000);

        //verify that the league is correctly deleted
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/leagues/" + uuid.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

    	uuid = UUID.randomUUID();
    	LeagueDto league1 = new LeagueDto(uuid, "Afebas");
        
    	//create 2 leagues
        this.leaguePublisherService.createLeagueAsync(league1);
        
    	uuid = UUID.randomUUID();
    	LeagueDto league2 = new LeagueDto(uuid, "FBEP");
        
        json = this.mapper.writeValueAsString(league2);
 
        this.leaguePublisherService.createLeagueAsync(league2);

        Thread.sleep(2000);
        //check if getLeagues return 2 leagues
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/leagues")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        this.leaguePublisherService.deleteLeagueAsync(league1.id());
        this.leaguePublisherService.deleteLeagueAsync(league2.id());
        
    }
}

