package fr.arnaud.cleanarchitecture.service;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import fr.arnaud.cleanarchitecture.core.model.Team;
import fr.arnaud.cleanarchitecture.core.repository.TeamRepository;
import fr.arnaud.cleanarchitecture.core.service.team.TeamService;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamServiceTest {

    @Autowired
    private TeamService teamService;

    @MockBean
    private TeamRepository teamRepository;

    @Test
    public void createTeam() throws Exception {
    	
    	UUID uuid = UUID.randomUUID();
    	Team team = Team.builder().id(uuid).name("Poule").build();
    		
        UUID teamId = this.teamService.createTeam(team);
        
        Assertions.assertThat(teamId).isNotNull();
    }

    @Test
    public void deleteTeam() throws Exception {
    	
    	UUID uuid = UUID.randomUUID();
    	Team team = Team.builder().id(uuid).name("Poule").build();
    		
        UUID teamId = this.teamService.createTeam(team);

        this.teamService.deleteTeam(teamId);
        
        Team existingTeam = this.teamService.getTeam(teamId);

        Assert.assertNull(existingTeam);
    }

    @Test
    public void testParameters() throws Exception {
    	
    	UUID uuid = UUID.randomUUID();
    	Team team = Team.builder().id(uuid).name("Poule").build();
    		
        UUID teamId = this.teamService.createTeam(team);

        this.teamService.deleteTeam(teamId);
        
        Team existingTeam = this.teamService.getTeam(teamId);

        Assert.assertNull(existingTeam);
    }
}
