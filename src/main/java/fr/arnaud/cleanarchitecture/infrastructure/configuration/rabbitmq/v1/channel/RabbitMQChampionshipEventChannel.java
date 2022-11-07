package fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.v1.channel;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class RabbitMQChampionshipEventChannel {

	private static final String CREATE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME = "event.v1.championship.create";
	
	private static final String UPDATE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME = "event.v1.championship.update";
    
	private static final String DELETE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME = "event.v1.championship.delete";

    @Bean
    public Declarables championshipEventDeclarables() {
    	
    	DirectExchange createChampionshipExchangeRequest = new DirectExchange(CREATE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME, true, false);
    	DirectExchange updateChampionshipExchangeRequest = new DirectExchange(UPDATE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME, true, false);
    	DirectExchange deleteChampionshipExchangeRequest = new DirectExchange(DELETE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME, true, false);
        
        return new Declarables(
        		createChampionshipExchangeRequest,
        		updateChampionshipExchangeRequest,
        		deleteChampionshipExchangeRequest
        );
    }
    
    // publisher    
    @Bean
    public MessageChannel createChampionshipEventV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createChampionshipEventV1OutboundChannel")
    public AmqpOutboundEndpoint createChampionshipEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
    public MessageChannel updateChampionshipEventV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateChampionshipEventV1OutboundChannel")
    public AmqpOutboundEndpoint updateChampionshipEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteChampionshipEventV1OutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "deleteChampionshipEventV1OutboundChannel")
    public AmqpOutboundEndpoint deleteChampionshipEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(DELETE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
