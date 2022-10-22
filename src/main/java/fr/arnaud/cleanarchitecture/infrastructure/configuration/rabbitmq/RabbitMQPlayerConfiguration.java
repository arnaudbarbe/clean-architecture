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

    private static final String CREATE_PLAYER_QUEUE_NAME_REQUEST_V1 = "createPlayerRequestV1";
    
    private static final String UPDATE_PLAYER_QUEUE_NAME_REQUEST_V1 = "updatePlayerRequestV1";

    private static final String DELETE_PLAYER_QUEUE_NAME_REQUEST_V1 = "deletePlayerRequestV1";

    private static final String CREATE_PLAYER_EXCHANGE_NAME_REQUEST_V1 = "createPlayerRequestV1";

    private static final String UPDATE_PLAYER_EXCHANGE_NAME_REQUEST_V1 = "updatePlayerRequestV1";

    private static final String DELETE_PLAYER_EXCHANGE_NAME_REQUEST_V1 = "deletePlayerRequestV1";

    @Autowired
    MessageCreatePlayerHandler messageCreatePlayerHandlerV1;
    
    @Autowired
    MessageUpdatePlayerHandler messageUpdatePlayerHandlerV1;
    
    @Autowired
    MessageDeletePlayerHandler messageDeletePlayerHandlerV1;

    @Bean
    public Declarables playerDeclarables() {
    	
    	Queue createPlayerRequestQueueV1 = new Queue(CREATE_PLAYER_QUEUE_NAME_REQUEST_V1, true, false, false);
    	Queue updatePlayerRequestQueueV1 = new Queue(UPDATE_PLAYER_QUEUE_NAME_REQUEST_V1, true, false, false);
    	Queue deletePlayerRequestQueueV1 = new Queue(DELETE_PLAYER_QUEUE_NAME_REQUEST_V1, true, false, false);

    	DirectExchange createPlayerExchangeV1 = new DirectExchange(CREATE_PLAYER_EXCHANGE_NAME_REQUEST_V1, true, false);
    	DirectExchange updatePlayerExchangeV1 = new DirectExchange(UPDATE_PLAYER_EXCHANGE_NAME_REQUEST_V1, true, false);
    	DirectExchange deletePlayerExchangeV1 = new DirectExchange(DELETE_PLAYER_EXCHANGE_NAME_REQUEST_V1, true, false);

        return new Declarables(
        		createPlayerRequestQueueV1,
        		updatePlayerRequestQueueV1,
        		deletePlayerRequestQueueV1,
        		createPlayerExchangeV1,
        		updatePlayerExchangeV1,
        		deletePlayerExchangeV1,
                BindingBuilder.bind(createPlayerRequestQueueV1).to(createPlayerExchangeV1).with("#"),
                BindingBuilder.bind(updatePlayerRequestQueueV1).to(updatePlayerExchangeV1).with("#"),
                BindingBuilder.bind(deletePlayerRequestQueueV1).to(deletePlayerExchangeV1).with("#")
        );
    }
    
    // consumer
    @Bean
    public IntegrationFlow createPlayerFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_PLAYER_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageCreatePlayerHandlerV1::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updatePlayerFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_PLAYER_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageUpdatePlayerHandlerV1::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deletePlayerFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_PLAYER_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageDeletePlayerHandlerV1::handle)
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
        outbound.setExchangeName(CREATE_PLAYER_EXCHANGE_NAME_REQUEST_V1);
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
        outbound.setExchangeName(UPDATE_PLAYER_EXCHANGE_NAME_REQUEST_V1);
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
        outbound.setExchangeName(DELETE_PLAYER_EXCHANGE_NAME_REQUEST_V1);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
