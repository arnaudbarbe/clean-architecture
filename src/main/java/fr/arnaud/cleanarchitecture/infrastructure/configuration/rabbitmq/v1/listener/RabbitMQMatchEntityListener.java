package fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.v1.listener;

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

import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreateMatchHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeleteMatchHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdateMatchHandler;

@Configuration
public class RabbitMQMatchEntityListener {

	public static final String CREATE_LEAGUE_QUEUE_NAME = "entity.v1.match.create";
    
	public static final String UPDATE_LEAGUE_QUEUE_NAME = "entity.v1.match.update";

	public static final String DELETE_LEAGUE_QUEUE_NAME = "entity.v1.match.delete";

	public static final String CREATE_LEAGUE_EXCHANGE_NAME = "entity.v1.match.create";
	
	public static final String UPDATE_LEAGUE_EXCHANGE_NAME = "entity.v1.match.update";
    
	public static final String DELETE_LEAGUE_EXCHANGE_NAME = "entity.v1.match.delete";

    @Autowired
    MessageCreateMatchHandler messageCreateMatchHandler;
    
    @Autowired
    MessageUpdateMatchHandler messageUpdateMatchHandler;
    
    @Autowired
    MessageDeleteMatchHandler messageDeleteMatchHandler;

    @Bean
    public Declarables matchDeclarables() {
    	
    	Queue createMatchRequestQueue = new Queue(CREATE_LEAGUE_QUEUE_NAME, true, false, false);
    	Queue updateMatchRequestQueue = new Queue(UPDATE_LEAGUE_QUEUE_NAME, true, false, false);
    	Queue deleteMatchRequestQueue = new Queue(DELETE_LEAGUE_QUEUE_NAME, true, false, false);

    	DirectExchange createMatchExchangeRequest = new DirectExchange(CREATE_LEAGUE_EXCHANGE_NAME, true, false);
    	DirectExchange updateMatchExchangeRequest = new DirectExchange(UPDATE_LEAGUE_EXCHANGE_NAME, true, false);
    	DirectExchange deleteMatchExchangeRequest = new DirectExchange(DELETE_LEAGUE_EXCHANGE_NAME, true, false);
        
        return new Declarables(
        		createMatchRequestQueue,
        		updateMatchRequestQueue,
        		deleteMatchRequestQueue,
        		createMatchExchangeRequest,
        		updateMatchExchangeRequest,
        		deleteMatchExchangeRequest,
                BindingBuilder.bind(createMatchRequestQueue).to(createMatchExchangeRequest).with("#"),
                BindingBuilder.bind(updateMatchRequestQueue).to(updateMatchExchangeRequest).with("#"),
                BindingBuilder.bind(deleteMatchRequestQueue).to(deleteMatchExchangeRequest).with("#")
        );
    }
    
    // consumer
    @Bean
    public IntegrationFlow createMatchFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_LEAGUE_QUEUE_NAME))
                .handle(this.messageCreateMatchHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updateMatchFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_LEAGUE_QUEUE_NAME))
                .handle(this.messageUpdateMatchHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteMatchFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_LEAGUE_QUEUE_NAME))
                .handle(this.messageDeleteMatchHandler::handle)
                .get();
    }
}
