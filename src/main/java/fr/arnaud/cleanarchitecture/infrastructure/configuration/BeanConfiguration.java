package fr.arnaud.cleanarchitecture.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import fr.arnaud.cleanarchitecture.CleanArchitectureApplication;
import fr.arnaud.cleanarchitecture.core.repository.OrderRepository;
import fr.arnaud.cleanarchitecture.core.service.DomainOrderService;
import fr.arnaud.cleanarchitecture.core.service.OrderService;

@Configuration
@ComponentScan(basePackageClasses = CleanArchitectureApplication.class)
public class BeanConfiguration {

    @Bean
    OrderService orderService(final OrderRepository orderRepository) {
        return new DomainOrderService(orderRepository);
    }
}
