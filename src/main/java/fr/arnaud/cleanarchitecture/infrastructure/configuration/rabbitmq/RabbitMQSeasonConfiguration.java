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

import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreateSeasonHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeleteSeasonHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdateSeasonHandler;

@Configuration
public class RabbitMQSeasonConfiguration {

    private static final String CREATE_SEASON_QUEUE_NAME = "createSeason";
    
    private static final String UPDATE_SEASON_QUEUE_NAME = "updateSeason";

    private static final String DELETE_SEASON_QUEUE_NAME = "deleteSeason";

    private static final String CREATE_SEASON_EXCHANGE_NAME = "createSeason";

    private static final String UPDATE_SEASON_EXCHANGE_NAME = "updateSeason";

    private static final String DELETE_SEASON_EXCHANGE_NAME = "deleteSeason";

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
    

    // publisher   
    @Bean
    public MessageChannel createSeasonAsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createSeasonAsyncOutboundChannel")
    public AmqpOutboundEndpoint createSeasonOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_SEASON_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel updateSeasonAsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateSeasonAsyncOutboundChannel")
    public AmqpOutboundEndpoint updateSeasonOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_SEASON_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteSeasonAsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "deleteSeasonAsyncOutboundChannel")
    public AmqpOutboundEndpoint deleteSeasonOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(DELETE_SEASON_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
