package fr.arnaud.cleanarchitecture.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import fr.arnaud.cleanarchitecture.core.model.Order;
import fr.arnaud.cleanarchitecture.core.model.Product;
import fr.arnaud.cleanarchitecture.core.repository.OrderRepository;
import fr.arnaud.cleanarchitecture.core.service.order.OrderService;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void getOrders() throws Exception {
    	
    	UUID productUuid = UUID.randomUUID();
    	Product product = new Product(productUuid, 12.3d, "parpaing");
    	UUID order1Uuid = UUID.randomUUID();
        Order order1 = new Order(order1Uuid, product);
    	UUID order2Uuid = UUID.randomUUID();
        Order order2 = new Order(order2Uuid, product);
      
        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        
        Mockito.when(this.orderRepository.findAll()).thenReturn(orders);
        
        List<Order> returnOrders = this.orderService.getOrders();
        Assertions.assertThat(returnOrders).hasSize(2);
    }
}
