package fr.arnaud.cleanarchitecture.service;

import java.math.BigDecimal;
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

import fr.arnaud.cleanarchitecture.core.model.Product;
import fr.arnaud.cleanarchitecture.core.repository.ProductRepository;
import fr.arnaud.cleanarchitecture.core.service.product.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void getProducts() throws Exception {
    	
    	List<Product> products = new ArrayList<>();
    	UUID productUuid = UUID.randomUUID();
    	Product product = new Product(productUuid, new BigDecimal(12.3d), "parpaing");
    	products.add(product);
    		
        Mockito.when(this.productRepository.findAll()).thenReturn(products);
        
        List<Product> returnProducts = this.productService.getProducts();
        Assertions.assertThat(returnProducts).hasSize(1);
    }
}
