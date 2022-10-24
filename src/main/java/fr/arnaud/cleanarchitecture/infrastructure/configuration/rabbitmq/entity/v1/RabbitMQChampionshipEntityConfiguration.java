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

import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreateChampionshipHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeleteChampionshipHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdateChampionshipHandler;

@Configuration
public class RabbitMQChampionshipEntityConfiguration {

	public static final String CREATE_CHAMPIONSHIP_QUEUE_NAME = "entity.v1.createChampionship";
    
	public static final String UPDATE_CHAMPIONSHIP_QUEUE_NAME = "entity.v1.updateChampionship";

	public static final String DELETE_CHAMPIONSHIP_QUEUE_NAME = "entity.v1.deleteChampionship";

	public static final String CREATE_CHAMPIONSHIP_EXCHANGE_NAME = "entity.v1.createChampionship";
	
	public static final String UPDATE_CHAMPIONSHIP_EXCHANGE_NAME = "entity.v1.updateChampionship";
    
	public static final String DELETE_CHAMPIONSHIP_EXCHANGE_NAME = "entity.v1.deleteChampionship";

    @Autowired
    MessageCreateChampionshipHandler messageCreateChampionshipHandler;
    
    @Autowired
    MessageUpdateChampionshipHandler messageUpdateChampionshipHandler;
    
    @Autowired
    MessageDeleteChampionshipHandler messageDeleteChampionshipHandler;

    @Bean
    public Declarables championshipDeclarables() {
    	
    	Queue createChampionshipRequestQueue = new Queue(CREATE_CHAMPIONSHIP_QUEUE_NAME, true, false, false);
    	Queue updateChampionshipRequestQueue = new Queue(UPDATE_CHAMPIONSHIP_QUEUE_NAME, true, false, false);
    	Queue deleteChampionshipRequestQueue = new Queue(DELETE_CHAMPIONSHIP_QUEUE_NAME, true, false, false);

    	DirectExchange createChampionshipExchangeRequest = new DirectExchange(CREATE_CHAMPIONSHIP_EXCHANGE_NAME, true, false);
    	DirectExchange updateChampionshipExchangeRequest = new DirectExchange(UPDATE_CHAMPIONSHIP_EXCHANGE_NAME, true, false);
    	DirectExchange deleteChampionshipExchangeRequest = new DirectExchange(DELETE_CHAMPIONSHIP_EXCHANGE_NAME, true, false);
        
        return new Declarables(
        		createChampionshipRequestQueue,
        		updateChampionshipRequestQueue,
        		deleteChampionshipRequestQueue,
        		createChampionshipExchangeRequest,
        		updateChampionshipExchangeRequest,
        		deleteChampionshipExchangeRequest,
                BindingBuilder.bind(createChampionshipRequestQueue).to(createChampionshipExchangeRequest).with("#"),
                BindingBuilder.bind(updateChampionshipRequestQueue).to(updateChampionshipExchangeRequest).with("#"),
                BindingBuilder.bind(deleteChampionshipRequestQueue).to(deleteChampionshipExchangeRequest).with("#")
        );
    }
    
    // consumer
    @Bean
    public IntegrationFlow createChampionshipFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_CHAMPIONSHIP_QUEUE_NAME))
                .handle(this.messageCreateChampionshipHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updateChampionshipFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_CHAMPIONSHIP_QUEUE_NAME))
                .handle(this.messageUpdateChampionshipHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteChampionshipFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_CHAMPIONSHIP_QUEUE_NAME))
                .handle(this.messageDeleteChampionshipHandler::handle)
                .get();
    }
}
