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

import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreateLeagueHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeleteLeagueHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdateLeagueHandler;

@Configuration
public class RabbitMQLeagueConfiguration {

	private static final String CREATE_LEAGUE_QUEUE_NAME_REQUEST_V1 = "createLeagueRequestV1";
    
	private static final String UPDATE_LEAGUE_QUEUE_NAME_REQUEST_V1 = "updateLeagueRequestV1";

	private static final String DELETE_LEAGUE_QUEUE_NAME_REQUEST_V1 = "deleteLeagueRequestV1";

	private static final String CREATE_LEAGUE_EXCHANGE_NAME_REQUEST_V1 = "createLeagueRequestV1";
	
	private static final String UPDATE_LEAGUE_EXCHANGE_NAME_REQUEST_V1 = "updateLeagueRequestV1";
    
	private static final String DELETE_LEAGUE_EXCHANGE_NAME_REQUEST_V1 = "deleteLeagueRequestV1";

    @Autowired
    MessageCreateLeagueHandler messageCreateLeagueHandlerV1;
    
    @Autowired
    MessageUpdateLeagueHandler messageUpdateLeagueHandlerV1;
    
    @Autowired
    MessageDeleteLeagueHandler messageDeleteLeagueHandlerV1;

    @Bean
    public Declarables leagueDeclarables() {
    	
    	Queue createLeagueRequestQueueV1 = new Queue(CREATE_LEAGUE_QUEUE_NAME_REQUEST_V1, true, false, false);
    	Queue updateLeagueRequestQueueV1 = new Queue(UPDATE_LEAGUE_QUEUE_NAME_REQUEST_V1, true, false, false);
    	Queue deleteLeagueRequestQueueV1 = new Queue(DELETE_LEAGUE_QUEUE_NAME_REQUEST_V1, true, false, false);

    	DirectExchange createLeagueExchangeRequestV1 = new DirectExchange(CREATE_LEAGUE_EXCHANGE_NAME_REQUEST_V1, true, false);
    	DirectExchange updateLeagueExchangeRequestV1 = new DirectExchange(UPDATE_LEAGUE_EXCHANGE_NAME_REQUEST_V1, true, false);
    	DirectExchange deleteLeagueExchangeRequestV1 = new DirectExchange(DELETE_LEAGUE_EXCHANGE_NAME_REQUEST_V1, true, false);
        
        return new Declarables(
        		createLeagueRequestQueueV1,
        		updateLeagueRequestQueueV1,
        		deleteLeagueRequestQueueV1,
        		createLeagueExchangeRequestV1,
        		updateLeagueExchangeRequestV1,
        		deleteLeagueExchangeRequestV1,
                BindingBuilder.bind(createLeagueRequestQueueV1).to(createLeagueExchangeRequestV1).with("#"),
                BindingBuilder.bind(updateLeagueRequestQueueV1).to(updateLeagueExchangeRequestV1).with("#"),
                BindingBuilder.bind(deleteLeagueRequestQueueV1).to(deleteLeagueExchangeRequestV1).with("#")
        );
    }
    
    // consumer
    @Bean
    public IntegrationFlow createLeagueFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_LEAGUE_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageCreateLeagueHandlerV1::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updateLeagueFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_LEAGUE_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageUpdateLeagueHandlerV1::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteLeagueFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_LEAGUE_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageDeleteLeagueHandlerV1::handle)
                .get();
    }
    

    // publisher    
    @Bean
    public MessageChannel createLeagueAsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createLeagueAsyncOutboundChannel")
    public AmqpOutboundEndpoint createLeagueOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_LEAGUE_EXCHANGE_NAME_REQUEST_V1);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
    public MessageChannel updateLeagueAsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateLeagueAsyncOutboundChannel")
    public AmqpOutboundEndpoint updateLeagueOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_LEAGUE_EXCHANGE_NAME_REQUEST_V1);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteLeagueAsyncOutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "deleteLeagueAsyncOutboundChannel")
    public AmqpOutboundEndpoint deleteLeagueOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(DELETE_LEAGUE_EXCHANGE_NAME_REQUEST_V1);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
