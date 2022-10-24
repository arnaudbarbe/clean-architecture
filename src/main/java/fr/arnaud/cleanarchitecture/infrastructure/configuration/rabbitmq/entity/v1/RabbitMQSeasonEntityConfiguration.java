package fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.entity.v1;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreateSeasonHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeleteSeasonHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdateSeasonHandler;

@Configuration
public class RabbitMQSeasonEntityConfiguration {

	public static final String CREATE_SEASON_QUEUE_NAME = "entity.v1.createSeason";
    
	public static final String UPDATE_SEASON_QUEUE_NAME = "entity.v1.updateSeason";

	public static final String DELETE_SEASON_QUEUE_NAME = "entity.v1.deleteSeason";

	public static final String CREATE_SEASON_EXCHANGE_NAME = "entity.v1.createSeason";

	public static final String UPDATE_SEASON_EXCHANGE_NAME = "entity.v1.updateSeason";

	public static final String DELETE_SEASON_EXCHANGE_NAME = "entity.v1.deleteSeason";

    @Autowired
    MessageCreateSeasonHandler messageCreateSeasonHandler;
    
    @Autowired
    MessageUpdateSeasonHandler messageUpdateSeasonHandler;
    
    @Autowired
    MessageDeleteSeasonHandler messageDeleteSeasonHandler;

    @Bean
    public Declarables seasonDeclarables() {
    	
    	Queue createSeasonQueue = new Queue(CREATE_SEASON_QUEUE_NAME, true, false, false);
    	Queue updateSeasonQueue = new Queue(UPDATE_SEASON_QUEUE_NAME, true, false, false);
    	Queue deleteSeasonQueue = new Queue(DELETE_SEASON_QUEUE_NAME, true, false, false);

    	DirectExchange createSeasonExchange = new DirectExchange(CREATE_SEASON_EXCHANGE_NAME, true, false);
    	DirectExchange updateSeasonExchange = new DirectExchange(UPDATE_SEASON_EXCHANGE_NAME, true, false);
    	DirectExchange deleteSeasonExchange = new DirectExchange(DELETE_SEASON_EXCHANGE_NAME, true, false);

        return new Declarables(
        		createSeasonQueue,
        		updateSeasonQueue,
        		deleteSeasonQueue,
        		createSeasonExchange,
        		updateSeasonExchange,
        		deleteSeasonExchange,
                BindingBuilder.bind(createSeasonQueue).to(createSeasonExchange).with("#"),
                BindingBuilder.bind(updateSeasonQueue).to(updateSeasonExchange).with("#"),
                BindingBuilder.bind(deleteSeasonQueue).to(deleteSeasonExchange).with("#")
        );
    }
    
    // consumer
    @Bean
    public IntegrationFlow createSeasonFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_SEASON_QUEUE_NAME))
                .handle(this.messageCreateSeasonHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updateSeasonFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_SEASON_QUEUE_NAME))
                .handle(this.messageUpdateSeasonHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteSeasonFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_SEASON_QUEUE_NAME))
                .handle(this.messageDeleteSeasonHandler::handle)
                .get();
    }
}
