package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.order;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.arnaud.cleanarchitecture.core.service.order.OrderService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.order.request.AddProductRequest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.order.request.CreateOrderRequest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.order.response.CreateOrderResponse;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.order.response.GetOrdersResponse;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.product.response.CreateProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    
    
    
    
    
    
    
	@PostMapping( 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.CREATED)

	@Operation(
			summary = "Create an order", 
			description = "Create an order bla bla")

    @ApiResponses(
    		value = {@ApiResponse(
    				responseCode = "201", 
    				description = "created",
    						content = @Content(
    						        schema = @Schema(implementation = CreateProductResponse.class, example = "f67546f1-5a47-4e86-b7a9-dbae57fbbb57")
    						)
    				)}
    		)

	@Tags({ 
		@Tag(name="Order")})
    public CreateOrderResponse createOrder(@RequestBody final CreateOrderRequest createOrderRequest) {
        final UUID id = this.orderService.createOrder(createOrderRequest.getProduct());

        return new CreateOrderResponse(id);
    }

	
	
	
	
	
	
	
	
	@PutMapping(
			value = "/{orderId}/products", 
			consumes = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.NO_CONTENT)

	@Operation(
			summary = "add a product", 
			description = "add a product to an order")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "204", 
					description = "no content") })

	@Tags({ 
		@Tag(name="Order")})
    public void addProduct(@PathVariable final UUID orderId, @RequestBody final AddProductRequest addProductRequest) {
        this.orderService.addProduct(orderId, addProductRequest.getProduct());
    }

	
	
	
	
	
	
	
	@DeleteMapping(
			value = "/{orderId}/products/{productId}")

	@ResponseStatus(code = HttpStatus.NO_CONTENT)

	@Operation(
			summary = "Delete a device", 
			description = "Delete a device by its identifier", 
			security = { @SecurityRequirement(name = "bearerAuth") })

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "204", 
					description = "no content") })

	@Tags({ 
		@Tag(name="Order")})
    public void deleteProduct(@PathVariable final UUID orderId, @PathVariable final UUID productId) {
        this.orderService.deleteProduct(orderId, productId);
    }

	
	
	
	
	
	
	
	
	
	
	
	
	@PutMapping(
			value = "/{orderId}/complete", 
			consumes = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.NO_CONTENT)

	@Operation(
			summary = "complete a product", 
			description = "complete a product blabla")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "204", 
					description = "no content") })

	@Tags({ 
		@Tag(name="Order")})
    public void completeOrder(@PathVariable final UUID id) {
        this.orderService.completeOrder(id);
    }
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    @GetMapping()
    public GetOrdersResponse getOrders() {
        return new GetOrdersResponse(this.orderService.getOrders());
    }
}