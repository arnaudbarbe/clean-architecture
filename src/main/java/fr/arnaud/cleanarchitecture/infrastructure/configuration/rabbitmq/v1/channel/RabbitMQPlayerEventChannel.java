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
public class RabbitMQPlayerEventChannel {

    private static final String CREATE_PLAYER_EVENT_EXCHANGE_NAME = "event.v1.player.create";

    private static final String UPDATE_PLAYER_EVENT_EXCHANGE_NAME = "event.v1.player.update";

    private static final String DELETE_PLAYER_EVENT_EXCHANGE_NAME = "event.v1.player.delete";

    @Bean
    public Declarables playerEventDeclarables() {
    	
    	DirectExchange createPlayerExchange = new DirectExchange(CREATE_PLAYER_EVENT_EXCHANGE_NAME, true, false);
    	DirectExchange updatePlayerExchange = new DirectExchange(UPDATE_PLAYER_EVENT_EXCHANGE_NAME, true, false);
    	DirectExchange deletePlayerExchange = new DirectExchange(DELETE_PLAYER_EVENT_EXCHANGE_NAME, true, false);

        return new Declarables(
        		createPlayerExchange,
        		updatePlayerExchange,
        		deletePlayerExchange
        );
    }
    
    // publisher   
    @Bean
    public MessageChannel createPlayerEventV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createPlayerEventV1OutboundChannel")
    public AmqpOutboundEndpoint createPlayerEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_PLAYER_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel updatePlayerEventV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updatePlayerEventV1OutboundChannel")
    public AmqpOutboundEndpoint updatePlayerEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_PLAYER_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deletePlayerEventV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "deletePlayerEventV1OutboundChannel")
    public AmqpOutboundEndpoint deletePlayerEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(DELETE_PLAYER_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
