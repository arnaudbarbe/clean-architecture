package fr.arnaud.cleanarchitecture.infrastructure.configuration.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import fr.arnaud.cleanarchitecture.CleanArchitectureApplication;
import fr.arnaud.cleanarchitecture.core.repository.ChampionshipRepository;
import fr.arnaud.cleanarchitecture.core.repository.LeagueRepository;
import fr.arnaud.cleanarchitecture.core.repository.MatchRepository;
import fr.arnaud.cleanarchitecture.core.repository.PlayerRepository;
import fr.arnaud.cleanarchitecture.core.repository.SeasonRepository;
import fr.arnaud.cleanarchitecture.core.repository.TeamRepository;
import fr.arnaud.cleanarchitecture.core.service.championship.ChampionshipService;
import fr.arnaud.cleanarchitecture.core.service.championship.DomainChampionshipService;
import fr.arnaud.cleanarchitecture.core.service.league.DomainLeagueService;
import fr.arnaud.cleanarchitecture.core.service.league.LeagueService;
import fr.arnaud.cleanarchitecture.core.service.match.DomainMatchService;
import fr.arnaud.cleanarchitecture.core.service.match.MatchService;
import fr.arnaud.cleanarchitecture.core.service.player.DomainPlayerService;
import fr.arnaud.cleanarchitecture.core.service.player.PlayerService;
import fr.arnaud.cleanarchitecture.core.service.season.DomainSeasonService;
import fr.arnaud.cleanarchitecture.core.service.season.SeasonService;
import fr.arnaud.cleanarchitecture.core.service.team.DomainTeamService;
import fr.arnaud.cleanarchitecture.core.service.team.TeamService;
import fr.arnaud.cleanarchitecture.infrastructure.service.AuthService;

@Configuration
@ComponentScan(basePackageClasses = CleanArchitectureApplication.class)
public class ServiceConfiguration {
	
    public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm";
    public static final LocalDateTime FIXED_DATE = LocalDateTime.now();
    public static final LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT));

    @Bean
    ChampionshipService championshipService(final ChampionshipRepository championshipRepository) {
        return new DomainChampionshipService(championshipRepository);
    }
    
    @Bean
    LeagueService leagueService(final LeagueRepository leagueRepository) {
        return new DomainLeagueService(leagueRepository);
    }
    
    @Bean
    MatchService matchService(final MatchRepository matchRepository) {
        return new DomainMatchService(matchRepository);
    }

    @Bean
    PlayerService playerService(final PlayerRepository playerRepository) {
        return new DomainPlayerService(playerRepository);
    }

    @Bean
    SeasonService seasonService(final SeasonRepository seasonRepository) {
        return new DomainSeasonService(seasonRepository);
    }

    @Bean
    TeamService teamService(final TeamRepository teamRepository) {
        return new DomainTeamService(teamRepository);
    }
    
    @Bean
    AuthService authService() {
        return new AuthService();
    }
    
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.serializationInclusion(JsonInclude.Include.NON_NULL)
          .serializers(LOCAL_DATETIME_SERIALIZER);
    }
}
