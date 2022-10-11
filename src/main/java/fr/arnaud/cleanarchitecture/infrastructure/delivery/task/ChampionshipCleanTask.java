package fr.arnaud.cleanarchitecture.infrastructure.delivery.task;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.championship.ChampionshipService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChampionshipCleanTask {

	@Autowired
	private ChampionshipService championshipService;

	@Scheduled(cron = "0 0/2 * * * *")
	public void cleanChampionship() {
		
		log.debug("========================== begin task ChampionshipCleanTask.cleanChampionship ====================");

		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = localDateTime.minusMonths(1);
		
		
		//this.championshipService.deleteByCreationDateLessThan(localDateTime);
		
		log.debug("========================== end task ChampionshipCleanTask.cleanChampionship ======================");
	}
}
