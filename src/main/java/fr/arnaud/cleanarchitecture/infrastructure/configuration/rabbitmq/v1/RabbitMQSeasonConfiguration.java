package fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.v1;

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

import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreateSeasonHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeleteSeasonHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdateSeasonHandler;

@Configuration
public class RabbitMQSeasonConfiguration {

    private static final String CREATE_SEASON_QUEUE_NAME_REQUEST = "createSeasonV1Request";
    
    private static final String UPDATE_SEASON_QUEUE_NAME_REQUEST = "updateSeasonV1Request";

    private static final String DELETE_SEASON_QUEUE_NAME_REQUEST = "deleteSeasonV1Request";

    private static final String CREATE_SEASON_EXCHANGE_NAME_REQUEST = "createSeasonV1Request";

    private static final String UPDATE_SEASON_EXCHANGE_NAME_REQUEST = "updateSeasonV1Request";

    private static final String DELETE_SEASON_EXCHANGE_NAME_REQUEST = "deleteSeasonV1Request";

    @Autowired
    MessageCreateSeasonHandler messageCreateSeasonHandler;
    
    @Autowired
    MessageUpdateSeasonHandler messageUpdateSeasonHandler;
    
    @Autowired
    MessageDeleteSeasonHandler messageDeleteSeasonHandler;

    @Bean
    public Declarables seasonDeclarables() {
    	
    	Queue createSeasonQueue = new Queue(CREATE_SEASON_QUEUE_NAME_REQUEST, true, false, false);
    	Queue updateSeasonQueue = new Queue(UPDATE_SEASON_QUEUE_NAME_REQUEST, true, false, false);
    	Queue deleteSeasonQueue = new Queue(DELETE_SEASON_QUEUE_NAME_REQUEST, true, false, false);

    	DirectExchange createSeasonExchange = new DirectExchange(CREATE_SEASON_EXCHANGE_NAME_REQUEST, true, false);
    	DirectExchange updateSeasonExchange = new DirectExchange(UPDATE_SEASON_EXCHANGE_NAME_REQUEST, true, false);
    	DirectExchange deleteSeasonExchange = new DirectExchange(DELETE_SEASON_EXCHANGE_NAME_REQUEST, true, false);

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
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_SEASON_QUEUE_NAME_REQUEST))
                .handle(this.messageCreateSeasonHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updateSeasonFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_SEASON_QUEUE_NAME_REQUEST))
                .handle(this.messageUpdateSeasonHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteSeasonFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_SEASON_QUEUE_NAME_REQUEST))
                .handle(this.messageDeleteSeasonHandler::handle)
                .get();
    }
    

    // publisher   
    @Bean
    public MessageChannel createSeasonV1AsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createSeasonV1AsyncOutboundChannel")
    public AmqpOutboundEndpoint createSeasonOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_SEASON_EXCHANGE_NAME_REQUEST);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel updateSeasonV1AsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateSeasonV1AsyncOutboundChannel")
    public AmqpOutboundEndpoint updateSeasonOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_SEASON_EXCHANGE_NAME_REQUEST);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteSeasonV1AsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "deleteSeasonV1AsyncOutboundChannel")
    public AmqpOutboundEndpoint deleteSeasonOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(DELETE_SEASON_EXCHANGE_NAME_REQUEST);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
