package fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreatePlayerHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeletePlayerHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdatePlayerHandler;

@Configuration
public class RabbitMQPlayerConfiguration {

    private static final String CREATE_PLAYER_QUEUE_NAME = "createPlayer";
    
    private static final String UPDATE_PLAYER_QUEUE_NAME = "updatePlayer";

    private static final String DELETE_PLAYER_QUEUE_NAME = "deletePlayer";

    private static final String CREATE_PLAYER_EXCHANGE_NAME = "createPlayer";

    private static final String UPDATE_PLAYER_EXCHANGE_NAME = "updatePlayer";

    private static final String DELETE_PLAYER_EXCHANGE_NAME = "deletePlayer";

    @Autowired
    MessageCreatePlayerHandler messageCreatePlayerHandler;
    
    @Autowired
    MessageUpdatePlayerHandler messageUpdatePlayerHandler;
    
    @Autowired
    MessageDeletePlayerHandler messageDeletePlayerHandler;

    @Bean
    public Declarables playerDeclarables() {
    	
    	Queue createPlayerQueue = new Queue(CREATE_PLAYER_QUEUE_NAME, true, false, false);
    	Queue updatePlayerQueue = new Queue(UPDATE_PLAYER_QUEUE_NAME, true, false, false);
    	Queue deletePlayerQueue = new Queue(DELETE_PLAYER_QUEUE_NAME, true, false, false);

    	DirectExchange createPlayerExchange = new DirectExchange(CREATE_PLAYER_EXCHANGE_NAME, true, false);
    	DirectExchange updatePlayerExchange = new DirectExchange(UPDATE_PLAYER_EXCHANGE_NAME, true, false);
    	DirectExchange deletePlayerExchange = new DirectExchange(DELETE_PLAYER_EXCHANGE_NAME, true, false);

        return new Declarables(
        		createPlayerQueue,
        		updatePlayerQueue,
        		deletePlayerQueue,
        		createPlayerExchange,
        		updatePlayerExchange,
        		deletePlayerExchange,
                BindingBuilder.bind(createPlayerQueue).to(createPlayerExchange).with("#"),
                BindingBuilder.bind(updatePlayerQueue).to(updatePlayerExchange).with("#"),
                BindingBuilder.bind(deletePlayerQueue).to(deletePlayerExchange).with("#")
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
    

    // publisher   
    @Bean
    public MessageChannel createPlayerAsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createPlayerAsyncOutboundChannel")
    public AmqpOutboundEndpoint createPlayerOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_PLAYER_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel updatePlayerAsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updatePlayerAsyncOutboundChannel")
    public AmqpOutboundEndpoint updatePlayerOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_PLAYER_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deletePlayerAsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "deletePlayerAsyncOutboundChannel")
    public AmqpOutboundEndpoint deletePlayerOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(this.DELETE_PLAYER_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
