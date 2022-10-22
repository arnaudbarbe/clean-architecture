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

    private static final String CREATE_SEASON_QUEUE_NAME_REQUEST_V1 = "createSeasonRequestV1";
    
    private static final String UPDATE_SEASON_QUEUE_NAME_REQUEST_V1 = "updateSeasonRequestV1";

    private static final String DELETE_SEASON_QUEUE_NAME_REQUEST_V1 = "deleteSeasonRequestV1";

    private static final String CREATE_SEASON_EXCHANGE_NAME_REQUEST_V1 = "createSeasonRequestV1";

    private static final String UPDATE_SEASON_EXCHANGE_NAME_REQUEST_V1 = "updateSeasonRequestV1";

    private static final String DELETE_SEASON_EXCHANGE_NAME_REQUEST_V1 = "deleteSeasonRequestV1";

    @Autowired
    MessageCreateSeasonHandler messageCreateSeasonHandlerV1;
    
    @Autowired
    MessageUpdateSeasonHandler messageUpdateSeasonHandlerV1;
    
    @Autowired
    MessageDeleteSeasonHandler messageDeleteSeasonHandlerV1;

    @Bean
    public Declarables seasonDeclarables() {
    	
    	Queue createSeasonQueueV1 = new Queue(CREATE_SEASON_QUEUE_NAME_REQUEST_V1, true, false, false);
    	Queue updateSeasonQueueV1 = new Queue(UPDATE_SEASON_QUEUE_NAME_REQUEST_V1, true, false, false);
    	Queue deleteSeasonQueueV1 = new Queue(DELETE_SEASON_QUEUE_NAME_REQUEST_V1, true, false, false);

    	DirectExchange createSeasonExchangeV1 = new DirectExchange(CREATE_SEASON_EXCHANGE_NAME_REQUEST_V1, true, false);
    	DirectExchange updateSeasonExchangeV1 = new DirectExchange(UPDATE_SEASON_EXCHANGE_NAME_REQUEST_V1, true, false);
    	DirectExchange deleteSeasonExchangeV1 = new DirectExchange(DELETE_SEASON_EXCHANGE_NAME_REQUEST_V1, true, false);

        return new Declarables(
        		createSeasonQueueV1,
        		updateSeasonQueueV1,
        		deleteSeasonQueueV1,
        		createSeasonExchangeV1,
        		updateSeasonExchangeV1,
        		deleteSeasonExchangeV1,
                BindingBuilder.bind(createSeasonQueueV1).to(createSeasonExchangeV1).with("#"),
                BindingBuilder.bind(updateSeasonQueueV1).to(updateSeasonExchangeV1).with("#"),
                BindingBuilder.bind(deleteSeasonQueueV1).to(deleteSeasonExchangeV1).with("#")
        );
    }
    
    // consumer
    @Bean
    public IntegrationFlow createSeasonFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_SEASON_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageCreateSeasonHandlerV1::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updateSeasonFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_SEASON_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageUpdateSeasonHandlerV1::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteSeasonFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_SEASON_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageDeleteSeasonHandlerV1::handle)
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
        outbound.setExchangeName(CREATE_SEASON_EXCHANGE_NAME_REQUEST_V1);
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
        outbound.setExchangeName(UPDATE_SEASON_EXCHANGE_NAME_REQUEST_V1);
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
        outbound.setExchangeName(DELETE_SEASON_EXCHANGE_NAME_REQUEST_V1);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
