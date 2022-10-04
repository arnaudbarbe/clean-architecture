package fr.arnaud.cleanarchitecture.infrastructure.persistence.mongo.order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.model.Order;
import fr.arnaud.cleanarchitecture.core.repository.OrderRepository;

@Component
@Primary
public class MongoDbOrderRepository implements OrderRepository {

    private final SpringDataMongoOrderRepository orderRepository;

    @Autowired
    public MongoDbOrderRepository(final SpringDataMongoOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<Order> findById(final UUID id) {
        return this.orderRepository.findById(id);
    }

    @Override
    public void save(final Order order) {
        this.orderRepository.save(order);
    }
    
	@Override
	public List<Order> findAll() {
		return this.orderRepository.findAll();
	}
}
