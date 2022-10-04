package fr.arnaud.cleanarchitecture.infrastructure.configuration.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import fr.arnaud.cleanarchitecture.CleanArchitectureApplication;
import fr.arnaud.cleanarchitecture.core.repository.OrderRepository;
import fr.arnaud.cleanarchitecture.core.repository.ProductRepository;
import fr.arnaud.cleanarchitecture.core.service.order.DomainOrderService;
import fr.arnaud.cleanarchitecture.core.service.order.OrderService;
import fr.arnaud.cleanarchitecture.core.service.product.DomainProductService;
import fr.arnaud.cleanarchitecture.core.service.product.ProductService;

@Configuration
@ComponentScan(basePackageClasses = CleanArchitectureApplication.class)
public class BeanConfiguration {

    @Bean
    OrderService orderService(final OrderRepository orderRepository) {
        return new DomainOrderService(orderRepository);
    }
    
    @Bean
    ProductService productService(final ProductRepository productRepository) {
        return new DomainProductService(productRepository);
    }
}
