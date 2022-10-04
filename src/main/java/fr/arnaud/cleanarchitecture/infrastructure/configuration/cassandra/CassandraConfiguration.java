package fr.arnaud.cleanarchitecture.infrastructure.configuration.cassandra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.infrastructure.persistence.cassandra.order.SpringDataCassandraOrderRepository;
import fr.arnaud.cleanarchitecture.infrastructure.persistence.cassandra.product.SpringDataCassandraProductRepository;

@EnableCassandraRepositories(
		basePackageClasses = {
				SpringDataCassandraOrderRepository.class, 
				SpringDataCassandraProductRepository.class})
@Component
public class CassandraConfiguration extends AbstractCassandraConfiguration {

	@Value("${spring.data.cassandra.keyspace-name}")
	private String keySpaceName;

/*	@Override
	protected ResourceKeyspacePopulator keyspacePopulator() {
	
		return new ResourceKeyspacePopulator(
				scriptOf("CREATE TABLE IF NOT EXISTS productentity ( "+
				"id text, " +
				"name text, " + 
				"price decimal, " + 
				"unit text, " + 
				"creationDate text, " + 
				"PRIMARY KEY ((id)));")
		);
	}*/

	@Override
	protected String getKeyspaceName() {
		return this.keySpaceName;
	}

}