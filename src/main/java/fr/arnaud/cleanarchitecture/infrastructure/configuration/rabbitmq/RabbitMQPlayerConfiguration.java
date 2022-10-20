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

    private static final String CREATE_PLAYER_QUEUE_NAME_REQUEST = "createPlayerRequest";
    
    private static final String UPDATE_PLAYER_QUEUE_NAME_REQUEST = "updatePlayerRequest";

    private static final String DELETE_PLAYER_QUEUE_NAME_REQUEST = "deletePlayerRequest";

    private static final String CREATE_PLAYER_EXCHANGE_NAME_REQUEST = "createPlayerRequest";

    private static final String UPDATE_PLAYER_EXCHANGE_NAME_REQUEST = "updatePlayerRequest";

    private static final String DELETE_PLAYER_EXCHANGE_NAME_REQUEST = "deletePlayerRequest";

    @Autowired
    MessageCreatePlayerHandler messageCreatePlayerHandler;
    
    @Autowired
    MessageUpdatePlayerHandler messageUpdatePlayerHandler;
    
    @Autowired
    MessageDeletePlayerHandler messageDeletePlayerHandler;

    @Bean
    public Declarables playerDeclarables() {
    	
    	Queue createPlayerRequestQueue = new Queue(CREATE_PLAYER_QUEUE_NAME_REQUEST, true, false, false);
    	Queue updatePlayerRequestQueue = new Queue(UPDATE_PLAYER_QUEUE_NAME_REQUEST, true, false, false);
    	Queue deletePlayerRequestQueue = new Queue(DELETE_PLAYER_QUEUE_NAME_REQUEST, true, false, false);

    	DirectExchange createPlayerExchange = new DirectExchange(CREATE_PLAYER_EXCHANGE_NAME_REQUEST, true, false);
    	DirectExchange updatePlayerExchange = new DirectExchange(UPDATE_PLAYER_EXCHANGE_NAME_REQUEST, true, false);
    	DirectExchange deletePlayerExchange = new DirectExchange(DELETE_PLAYER_EXCHANGE_NAME_REQUEST, true, false);

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
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_PLAYER_QUEUE_NAME_REQUEST))
                .handle(this.messageCreatePlayerHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updatePlayerFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_PLAYER_QUEUE_NAME_REQUEST))
                .handle(this.messageUpdatePlayerHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deletePlayerFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_PLAYER_QUEUE_NAME_REQUEST))
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
        outbound.setExchangeName(CREATE_PLAYER_EXCHANGE_NAME_REQUEST);
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
        outbound.setExchangeName(UPDATE_PLAYER_EXCHANGE_NAME_REQUEST);
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
        outbound.setExchangeName(DELETE_PLAYER_EXCHANGE_NAME_REQUEST);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
