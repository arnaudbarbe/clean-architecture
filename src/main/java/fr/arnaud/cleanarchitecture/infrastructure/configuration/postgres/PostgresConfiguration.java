package fr.arnaud.cleanarchitecture.infrastructure.configuration.postgres;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.championship.ChampionshipEntity;
import fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.championship.SpringDataPostgresChampionshipRepository;
import fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.league.LeagueEntity;
import fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.league.SpringDataPostgresLeagueRepository;
import fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.player.PlayerEntity;
import fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.player.SpringDataPostgresPlayerRepository;
import fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.season.SeasonEntity;
import fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.season.SpringDataPostgresSeasonRepository;
import fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.team.SpringDataPostgresTeamRepository;
import fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.team.TeamEntity;

@EnableJpaRepositories(
		basePackageClasses = {
			SpringDataPostgresChampionshipRepository.class,
			SpringDataPostgresLeagueRepository.class,
			SpringDataPostgresPlayerRepository.class,
			SpringDataPostgresSeasonRepository.class,
			SpringDataPostgresTeamRepository.class})
@EntityScan(
		basePackageClasses = {
				ChampionshipEntity.class,
				LeagueEntity.class,
				PlayerEntity.class,
				SeasonEntity.class,
				TeamEntity.class
		})
@EnableTransactionManagement
@Component
public class PostgresConfiguration {

}
