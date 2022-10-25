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

import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreatePlayerHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeletePlayerHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdatePlayerHandler;

@Configuration
public class RabbitMQPlayerEntityConfiguration {

	public static final String CREATE_PLAYER_QUEUE_NAME = "entity.v1.player.create";
    
	public static final String UPDATE_PLAYER_QUEUE_NAME = "entity.v1.player.update";

	public static final String DELETE_PLAYER_QUEUE_NAME = "entity.v1.player.delete";

	public static final String CREATE_PLAYER_EXCHANGE_NAME = "entity.v1.player.create";

	public static final String UPDATE_PLAYER_EXCHANGE_NAME = "entity.v1.player.update";

	public static final String DELETE_PLAYER_EXCHANGE_NAME = "entity.v1.player.delete";

    @Autowired
    MessageCreatePlayerHandler messageCreatePlayerHandler;
    
    @Autowired
    MessageUpdatePlayerHandler messageUpdatePlayerHandler;
    
    @Autowired
    MessageDeletePlayerHandler messageDeletePlayerHandler;

    @Bean
    public Declarables playerDeclarables() {
    	
    	Queue createPlayerRequestQueue = new Queue(CREATE_PLAYER_QUEUE_NAME, true, false, false);
    	Queue updatePlayerRequestQueue = new Queue(UPDATE_PLAYER_QUEUE_NAME, true, false, false);
    	Queue deletePlayerRequestQueue = new Queue(DELETE_PLAYER_QUEUE_NAME, true, false, false);

    	DirectExchange createPlayerExchange = new DirectExchange(CREATE_PLAYER_EXCHANGE_NAME, true, false);
    	DirectExchange updatePlayerExchange = new DirectExchange(UPDATE_PLAYER_EXCHANGE_NAME, true, false);
    	DirectExchange deletePlayerExchange = new DirectExchange(DELETE_PLAYER_EXCHANGE_NAME, true, false);

        return new Declarables(
        		createPlayerRequestQueue,
        		updatePlayerRequestQueue,
        		deletePlayerRequestQueue,
        		createPlayerExchange,
        		updatePlayerExchange,
        		deletePlayerExchange,
                BindingBuilder.bind(createPlayerRequestQueue).to(createPlayerExchange).with("#"),
                BindingBuilder.bind(updatePlayerRequestQueue).to(updatePlayerExchange).with("#"),
                BindingBuilder.bind(deletePlayerRequestQueue).to(deletePlayerExchange).with("#")
        );
    }
    
    // consumer
    @Bean
    public IntegrationFlow createPlayerFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_PLAYER_QUEUE_NAME))
                .handle(this.messageCreatePlayerHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updatePlayerFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_PLAYER_QUEUE_NAME))
                .handle(this.messageUpdatePlayerHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deletePlayerFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_PLAYER_QUEUE_NAME))
                .handle(this.messageDeletePlayerHandler::handle)
                .get();
    }
}
