package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.order;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.arnaud.cleanarchitecture.core.service.OrderService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.order.request.AddProductRequest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.order.request.CreateOrderRequest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.order.response.CreateOrderResponse;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.order.response.GetOrdersResponse;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateOrderResponse createOrder(@RequestBody final CreateOrderRequest createOrderRequest) {
        final UUID id = this.orderService.createOrder(createOrderRequest.getProduct());

        return new CreateOrderResponse(id);
    }

    @PostMapping(value = "/{id}/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addProduct(@PathVariable final UUID id, @RequestBody final AddProductRequest addProductRequest) {
        this.orderService.addProduct(id, addProductRequest.getProduct());
    }

    @DeleteMapping(value = "/{id}/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteProduct(@PathVariable final UUID id, @RequestParam final UUID productId) {
        this.orderService.deleteProduct(id, productId);
    }

    @PostMapping("/{id}/complete")
    public void completeOrder(@PathVariable final UUID id) {
        this.orderService.completeOrder(id);
    }
    
    @GetMapping()
    public GetOrdersResponse getOrders() {
        return new GetOrdersResponse(this.orderService.getOrders());
    }
}