package fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.event.v1;

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
public class RabbitMQMatchEventConfiguration {

	private static final String CREATE_LEAGUE_EVENT_EXCHANGE_NAME = "event.v1.match.create";
	
	private static final String UPDATE_LEAGUE_EVENT_EXCHANGE_NAME = "event.v1.match.update";
    
	private static final String DELETE_LEAGUE_EVENT_EXCHANGE_NAME = "event.v1.match.delete";

    @Bean
    public Declarables matchEventDeclarables() {
    	
    	DirectExchange createMatchExchangeRequest = new DirectExchange(CREATE_LEAGUE_EVENT_EXCHANGE_NAME, true, false);
    	DirectExchange updateMatchExchangeRequest = new DirectExchange(UPDATE_LEAGUE_EVENT_EXCHANGE_NAME, true, false);
    	DirectExchange deleteMatchExchangeRequest = new DirectExchange(DELETE_LEAGUE_EVENT_EXCHANGE_NAME, true, false);
        
        return new Declarables(
        		createMatchExchangeRequest,
        		updateMatchExchangeRequest,
        		deleteMatchExchangeRequest
        );
    }
    
    // publisher    
    @Bean
    public MessageChannel createMatchEventV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createMatchEventV1OutboundChannel")
    public AmqpOutboundEndpoint createMatchEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_LEAGUE_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
    public MessageChannel updateMatchEventV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateMatchEventV1OutboundChannel")
    public AmqpOutboundEndpoint updateMatchEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_LEAGUE_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteMatchEventV1OutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "deleteMatchEventV1OutboundChannel")
    public AmqpOutboundEndpoint deleteMatchEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(DELETE_LEAGUE_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
