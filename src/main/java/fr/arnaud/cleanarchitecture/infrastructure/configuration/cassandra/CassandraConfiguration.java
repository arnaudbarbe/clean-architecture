package fr.arnaud.cleanarchitecture.infrastructure.configuration.cassandra;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.SessionBuilderConfigurer;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.core.cql.session.init.ResourceKeyspacePopulator;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.stereotype.Component;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;

@EnableCassandraRepositories(
		basePackages = {"fr.arnaud.cleanarchitecture.infrastructure.persistence"})
@Component
public class CassandraConfiguration extends AbstractCassandraConfiguration {

	@Value("${spring.data.cassandra.keyspace-name}")
	private String keySpaceName;
	
	@Value("${spring.data.cassandra.schema-action}")
	private String schemaAction;

	@Value("${spring.data.cassandra.contact-points}")
	private String contactPoints;

	@Value("${spring.data.cassandra.local-datacenter}")
	private String localDatacenter;

	@Override
	public String getContactPoints() {
		return this.contactPoints;
	}

	@Override
	protected String getLocalDataCenter() {
		return this.localDatacenter;
	}

	@Bean
	@Primary
	public CqlSessionFactoryBean session() {

		CqlSessionFactoryBean session = new CqlSessionFactoryBean();
		session.setContactPoints(getContactPoints());
		session.setKeyspaceName(getKeyspaceName());
		session.setLocalDatacenter(getLocalDataCenter());
		session.setPort(getPort());
		session.setKeyspaceCreations(getKeyspaceCreations());
		
		SessionBuilderConfigurer sessionBuilderConfigurer = cqlSessionBuilder -> cqlSessionBuilder
					
	                .withConfigLoader(
	                    		DriverConfigLoader.programmaticBuilder()
	                    		.withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofMillis(200000))
	                    		.build())
	               
	                ;

		session.setSessionBuilderConfigurer(sessionBuilderConfigurer);
		return session;
	}
	
	@Override
	protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
		CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(getKeyspaceName())
				.ifNotExists().with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication();
		return Arrays.asList(specification);
	}

	@Bean
	@Primary
	public SessionFactoryFactoryBean sessionFactory(final CqlSession session, final CassandraConverter converter) {
		SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
		sessionFactory.setSession(session);
		sessionFactory.setConverter(converter);
		sessionFactory.setSchemaAction(SchemaAction.valueOf(this.schemaAction));
		return sessionFactory;
	}

	@Bean
	@Primary
	public CassandraMappingContext mappingContext(final CqlSession cqlSession) {

		CassandraMappingContext mappingContext = new CassandraMappingContext();
		mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cqlSession));
		return mappingContext;
	}

	@Bean
	@Primary
	public CassandraConverter converter(final CassandraMappingContext mappingContext) {
		return new MappingCassandraConverter(mappingContext);
	}

	@Override
	protected ResourceKeyspacePopulator keyspacePopulator() {
	
		return new ResourceKeyspacePopulator(
				scriptOf("CREATE TABLE IF NOT EXISTS player ( "+
				"id uuid, " +
				"firstName text, " + 
				"lastName text, " + 
				"PRIMARY KEY ((id)));")
		);
	}

	@Override
	protected String getKeyspaceName() {
		return this.keySpaceName;
	}
}