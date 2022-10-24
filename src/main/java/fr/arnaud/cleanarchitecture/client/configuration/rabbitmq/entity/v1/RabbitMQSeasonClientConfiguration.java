package fr.arnaud.cleanarchitecture.client.configuration.rabbitmq.entity.v1;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

import fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.entity.v1.RabbitMQSeasonEntityConfiguration;

@Configuration
public class RabbitMQSeasonClientConfiguration {

    // publisher   
    @Bean
    public MessageChannel createSeasonV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createSeasonV1OutboundChannel")
    public AmqpOutboundEndpoint createSeasonOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQSeasonEntityConfiguration.CREATE_SEASON_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel updateSeasonV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateSeasonV1OutboundChannel")
    public AmqpOutboundEndpoint updateSeasonOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQSeasonEntityConfiguration.UPDATE_SEASON_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteSeasonV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "deleteSeasonV1OutboundChannel")
    public AmqpOutboundEndpoint deleteSeasonOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQSeasonEntityConfiguration.DELETE_SEASON_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
