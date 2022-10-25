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

import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreateLeagueHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeleteLeagueHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdateLeagueHandler;

@Configuration
public class RabbitMQLeagueEntityConfiguration {

	public static final String CREATE_LEAGUE_QUEUE_NAME = "entity.v1.league.create";
    
	public static final String UPDATE_LEAGUE_QUEUE_NAME = "entity.v1.league.update";

	public static final String DELETE_LEAGUE_QUEUE_NAME = "entity.v1.league.delete";

	public static final String CREATE_LEAGUE_EXCHANGE_NAME = "entity.v1.league.create";
	
	public static final String UPDATE_LEAGUE_EXCHANGE_NAME = "entity.v1.league.update";
    
	public static final String DELETE_LEAGUE_EXCHANGE_NAME = "entity.v1.league.delete";

    @Autowired
    MessageCreateLeagueHandler messageCreateLeagueHandler;
    
    @Autowired
    MessageUpdateLeagueHandler messageUpdateLeagueHandler;
    
    @Autowired
    MessageDeleteLeagueHandler messageDeleteLeagueHandler;

    @Bean
    public Declarables leagueDeclarables() {
    	
    	Queue createLeagueRequestQueue = new Queue(CREATE_LEAGUE_QUEUE_NAME, true, false, false);
    	Queue updateLeagueRequestQueue = new Queue(UPDATE_LEAGUE_QUEUE_NAME, true, false, false);
    	Queue deleteLeagueRequestQueue = new Queue(DELETE_LEAGUE_QUEUE_NAME, true, false, false);

    	DirectExchange createLeagueExchangeRequest = new DirectExchange(CREATE_LEAGUE_EXCHANGE_NAME, true, false);
    	DirectExchange updateLeagueExchangeRequest = new DirectExchange(UPDATE_LEAGUE_EXCHANGE_NAME, true, false);
    	DirectExchange deleteLeagueExchangeRequest = new DirectExchange(DELETE_LEAGUE_EXCHANGE_NAME, true, false);
        
        return new Declarables(
        		createLeagueRequestQueue,
        		updateLeagueRequestQueue,
        		deleteLeagueRequestQueue,
        		createLeagueExchangeRequest,
        		updateLeagueExchangeRequest,
        		deleteLeagueExchangeRequest,
                BindingBuilder.bind(createLeagueRequestQueue).to(createLeagueExchangeRequest).with("#"),
                BindingBuilder.bind(updateLeagueRequestQueue).to(updateLeagueExchangeRequest).with("#"),
                BindingBuilder.bind(deleteLeagueRequestQueue).to(deleteLeagueExchangeRequest).with("#")
        );
    }
    
    // consumer
    @Bean
    public IntegrationFlow createLeagueFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_LEAGUE_QUEUE_NAME))
                .handle(this.messageCreateLeagueHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updateLeagueFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_LEAGUE_QUEUE_NAME))
                .handle(this.messageUpdateLeagueHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteLeagueFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_LEAGUE_QUEUE_NAME))
                .handle(this.messageDeleteLeagueHandler::handle)
                .get();
    }
}
