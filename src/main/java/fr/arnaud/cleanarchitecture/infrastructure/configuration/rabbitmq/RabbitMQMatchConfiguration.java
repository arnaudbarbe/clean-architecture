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

import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreateMatchHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeleteMatchHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdateMatchHandler;

@Configuration
public class RabbitMQMatchConfiguration {

	private static final String CREATE_LEAGUE_QUEUE_NAME_REQUEST_V1 = "createMatchRequestV1";
    
	private static final String UPDATE_LEAGUE_QUEUE_NAME_REQUEST_V1 = "updateMatchRequestV1";

	private static final String DELETE_LEAGUE_QUEUE_NAME_REQUEST_V1 = "deleteMatchRequestV1";

	private static final String CREATE_LEAGUE_EXCHANGE_NAME_REQUEST_V1 = "createMatchRequestV1";
	
	private static final String UPDATE_LEAGUE_EXCHANGE_NAME_REQUEST_V1 = "updateMatchRequestV1";
    
	private static final String DELETE_LEAGUE_EXCHANGE_NAME_REQUEST_V1 = "deleteMatchRequestV1";

    @Autowired
    MessageCreateMatchHandler messageCreateMatchHandlerV1;
    
    @Autowired
    MessageUpdateMatchHandler messageUpdateMatchHandlerV1;
    
    @Autowired
    MessageDeleteMatchHandler messageDeleteMatchHandlerV1;

    @Bean
    public Declarables matchDeclarables() {
    	
    	Queue createMatchRequestQueueV1 = new Queue(CREATE_LEAGUE_QUEUE_NAME_REQUEST_V1, true, false, false);
    	Queue updateMatchRequestQueueV1 = new Queue(UPDATE_LEAGUE_QUEUE_NAME_REQUEST_V1, true, false, false);
    	Queue deleteMatchRequestQueueV1 = new Queue(DELETE_LEAGUE_QUEUE_NAME_REQUEST_V1, true, false, false);

    	DirectExchange createMatchExchangeRequestV1 = new DirectExchange(CREATE_LEAGUE_EXCHANGE_NAME_REQUEST_V1, true, false);
    	DirectExchange updateMatchExchangeRequestV1 = new DirectExchange(UPDATE_LEAGUE_EXCHANGE_NAME_REQUEST_V1, true, false);
    	DirectExchange deleteMatchExchangeRequestV1 = new DirectExchange(DELETE_LEAGUE_EXCHANGE_NAME_REQUEST_V1, true, false);
        
        return new Declarables(
        		createMatchRequestQueueV1,
        		updateMatchRequestQueueV1,
        		deleteMatchRequestQueueV1,
        		createMatchExchangeRequestV1,
        		updateMatchExchangeRequestV1,
        		deleteMatchExchangeRequestV1,
                BindingBuilder.bind(createMatchRequestQueueV1).to(createMatchExchangeRequestV1).with("#"),
                BindingBuilder.bind(updateMatchRequestQueueV1).to(updateMatchExchangeRequestV1).with("#"),
                BindingBuilder.bind(deleteMatchRequestQueueV1).to(deleteMatchExchangeRequestV1).with("#")
        );
    }
    
    // consumer
    @Bean
    public IntegrationFlow createMatchFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_LEAGUE_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageCreateMatchHandlerV1::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updateMatchFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_LEAGUE_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageUpdateMatchHandlerV1::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteMatchFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_LEAGUE_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageDeleteMatchHandlerV1::handle)
                .get();
    }
    

    // publisher    
    @Bean
    public MessageChannel createMatchAsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createMatchAsyncOutboundChannel")
    public AmqpOutboundEndpoint createMatchOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_LEAGUE_EXCHANGE_NAME_REQUEST_V1);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
    public MessageChannel updateMatchAsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateMatchAsyncOutboundChannel")
    public AmqpOutboundEndpoint updateMatchOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_LEAGUE_EXCHANGE_NAME_REQUEST_V1);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteMatchAsyncOutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "deleteMatchAsyncOutboundChannel")
    public AmqpOutboundEndpoint deleteMatchOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(DELETE_LEAGUE_EXCHANGE_NAME_REQUEST_V1);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
