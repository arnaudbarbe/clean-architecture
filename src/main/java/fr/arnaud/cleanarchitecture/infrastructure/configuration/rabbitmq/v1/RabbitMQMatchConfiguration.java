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

import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreateMatchHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeleteMatchHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdateMatchHandler;

@Configuration
public class RabbitMQMatchConfiguration {

	private static final String CREATE_LEAGUE_QUEUE_NAME_REQUEST = "createMatchV1Request";
    
	private static final String UPDATE_LEAGUE_QUEUE_NAME_REQUEST = "updateMatchV1Request";

	private static final String DELETE_LEAGUE_QUEUE_NAME_REQUEST = "deleteMatchV1Request";

	private static final String CREATE_LEAGUE_EXCHANGE_NAME_REQUEST = "createMatchV1Request";
	
	private static final String UPDATE_LEAGUE_EXCHANGE_NAME_REQUEST = "updateMatchV1Request";
    
	private static final String DELETE_LEAGUE_EXCHANGE_NAME_REQUEST = "deleteMatchV1Request";

    @Autowired
    MessageCreateMatchHandler messageCreateMatchHandler;
    
    @Autowired
    MessageUpdateMatchHandler messageUpdateMatchHandler;
    
    @Autowired
    MessageDeleteMatchHandler messageDeleteMatchHandler;

    @Bean
    public Declarables matchDeclarables() {
    	
    	Queue createMatchRequestQueue = new Queue(CREATE_LEAGUE_QUEUE_NAME_REQUEST, true, false, false);
    	Queue updateMatchRequestQueue = new Queue(UPDATE_LEAGUE_QUEUE_NAME_REQUEST, true, false, false);
    	Queue deleteMatchRequestQueue = new Queue(DELETE_LEAGUE_QUEUE_NAME_REQUEST, true, false, false);

    	DirectExchange createMatchExchangeRequest = new DirectExchange(CREATE_LEAGUE_EXCHANGE_NAME_REQUEST, true, false);
    	DirectExchange updateMatchExchangeRequest = new DirectExchange(UPDATE_LEAGUE_EXCHANGE_NAME_REQUEST, true, false);
    	DirectExchange deleteMatchExchangeRequest = new DirectExchange(DELETE_LEAGUE_EXCHANGE_NAME_REQUEST, true, false);
        
        return new Declarables(
        		createMatchRequestQueue,
        		updateMatchRequestQueue,
        		deleteMatchRequestQueue,
        		createMatchExchangeRequest,
        		updateMatchExchangeRequest,
        		deleteMatchExchangeRequest,
                BindingBuilder.bind(createMatchRequestQueue).to(createMatchExchangeRequest).with("#"),
                BindingBuilder.bind(updateMatchRequestQueue).to(updateMatchExchangeRequest).with("#"),
                BindingBuilder.bind(deleteMatchRequestQueue).to(deleteMatchExchangeRequest).with("#")
        );
    }
    
    // consumer
    @Bean
    public IntegrationFlow createMatchFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_LEAGUE_QUEUE_NAME_REQUEST))
                .handle(this.messageCreateMatchHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updateMatchFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_LEAGUE_QUEUE_NAME_REQUEST))
                .handle(this.messageUpdateMatchHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteMatchFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_LEAGUE_QUEUE_NAME_REQUEST))
                .handle(this.messageDeleteMatchHandler::handle)
                .get();
    }
    

    // publisher    
    @Bean
    public MessageChannel createMatchV1AsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createMatchV1AsyncOutboundChannel")
    public AmqpOutboundEndpoint createMatchOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_LEAGUE_EXCHANGE_NAME_REQUEST);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
    public MessageChannel updateMatchV1AsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateMatchV1AsyncOutboundChannel")
    public AmqpOutboundEndpoint updateMatchOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_LEAGUE_EXCHANGE_NAME_REQUEST);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteMatchV1AsyncOutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "deleteMatchV1AsyncOutboundChannel")
    public AmqpOutboundEndpoint deleteMatchOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(DELETE_LEAGUE_EXCHANGE_NAME_REQUEST);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
