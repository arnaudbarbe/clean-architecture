package fr.arnaud.cleanarchitecture.client.configuration.rabbitmq.entity.v1;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

import fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.entity.v1.RabbitMQPlayerEntityConfiguration;

@Configuration
public class RabbitMQPlayerClientConfiguration {

    // publisher   
    @Bean
    public MessageChannel createPlayerV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createPlayerV1OutboundChannel")
    public AmqpOutboundEndpoint createPlayerOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQPlayerEntityConfiguration.CREATE_PLAYER_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel updatePlayerV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updatePlayerV1OutboundChannel")
    public AmqpOutboundEndpoint updatePlayerOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQPlayerEntityConfiguration.UPDATE_PLAYER_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deletePlayerV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "deletePlayerV1OutboundChannel")
    public AmqpOutboundEndpoint deletePlayerOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQPlayerEntityConfiguration.DELETE_PLAYER_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
