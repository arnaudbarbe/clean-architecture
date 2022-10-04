package fr.arnaud.cleanarchitecture.infrastructure.delivery.task;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.arnaud.cleanarchitecture.core.service.product.ProductService;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ProductCleanTask {

	@Autowired
	private ProductService productService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductCleanTask.class);
	
	@Scheduled(cron = "0 0/2 * * * *")
	public void cleanProducts() {
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("========================== begin task ProductCleanTask.cleanProducts ====================");
		}

		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = localDateTime.minusMonths(1);
		
		
		this.productService.deleteByCreationDateLessThan(localDateTime);
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("========================== end task ProductCleanTask.cleanProducts ======================");
		}

	}
}
