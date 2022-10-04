package fr.arnaud.cleanarchitecture.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.arnaud.cleanarchitecture.core.model.Order;
import fr.arnaud.cleanarchitecture.core.model.Product;
import fr.arnaud.cleanarchitecture.core.service.order.OrderService;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private ObjectMapper mapper = new ObjectMapper();
    
    @Test
    public void createOrder() throws Exception {
    	
    	UUID uuid = UUID.randomUUID();
        Product product = new Product(uuid, 12.3d, "parpaing");
        
        String json = this.mapper.writeValueAsString(product);
        
        Mockito.when(this.orderService.createOrder(product)).thenReturn(uuid);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/orders")
        		.content(json)
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
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
        
        Mockito.when(this.orderService.getOrders()).thenReturn(orders);
        
        ResultActions actions = this.mockMvc.perform(MockMvcRequestBuilders.get("/orders")
        		.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
        
        MvcResult result = actions.andReturn();
        
        actions.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderItems[0].product.name", CoreMatchers.is("parpaing")));

    }
}

