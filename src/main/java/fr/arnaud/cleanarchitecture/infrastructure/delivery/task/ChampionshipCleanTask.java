package fr.arnaud.cleanarchitecture.infrastructure.delivery.task;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.arnaud.cleanarchitecture.core.service.championship.ChampionshipService;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ChampionshipCleanTask {

	@Autowired
	private ChampionshipService championshipService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ChampionshipCleanTask.class);
	
	@Scheduled(cron = "0 0/2 * * * *")
	public void cleanChampionship() {
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("========================== begin task ChampionshipCleanTask.cleanChampionship ====================");
		}

		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = localDateTime.minusMonths(1);
		
		
		//this.championshipService.deleteByCreationDateLessThan(localDateTime);
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("========================== end task ChampionshipCleanTask.cleanChampionship ======================");
		}

	}
}
