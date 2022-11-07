package fr.arnaud.cleanarchitecture.client.configuration.rabbitmq.v1.channel;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

import fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.v1.listener.RabbitMQLeagueEntityListener;

@Configuration
public class RabbitMQLeagueChannel {

    // publisher    
    @Bean
    public MessageChannel createLeagueV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createLeagueV1OutboundChannel")
    public AmqpOutboundEndpoint createLeagueOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQLeagueEntityListener.CREATE_LEAGUE_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
    public MessageChannel updateLeagueV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateLeagueV1OutboundChannel")
    public AmqpOutboundEndpoint updateLeagueOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQLeagueEntityListener.UPDATE_LEAGUE_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteLeagueV1OutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "deleteLeagueV1OutboundChannel")
    public AmqpOutboundEndpoint deleteLeagueOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQLeagueEntityListener.DELETE_LEAGUE_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
