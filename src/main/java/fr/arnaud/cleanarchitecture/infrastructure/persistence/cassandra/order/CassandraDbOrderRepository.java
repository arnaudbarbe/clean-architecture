package fr.arnaud.cleanarchitecture.infrastructure.persistence.cassandra.order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.model.Order;
import fr.arnaud.cleanarchitecture.core.repository.OrderRepository;

@Component("CassandraDbOrderRepository")
public class CassandraDbOrderRepository implements OrderRepository {

    private final SpringDataCassandraOrderRepository orderRepository;

    @Autowired
    public CassandraDbOrderRepository(final SpringDataCassandraOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<Order> findById(final UUID id) {
        Optional<OrderEntity> orderEntity = this.orderRepository.findById(id);
        if (orderEntity.isPresent()) {
            return Optional.of(orderEntity.get()
                .toOrder());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void save(final Order order) {
        this.orderRepository.save(new OrderEntity(order));
    }

	@Override
	public List<Order> findAll() {

		return this.orderRepository.findAll()
		.stream()
		.map(OrderEntity::toOrder).toList();
	}
}
