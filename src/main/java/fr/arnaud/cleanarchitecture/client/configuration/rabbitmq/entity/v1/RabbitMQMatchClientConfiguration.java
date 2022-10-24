package fr.arnaud.cleanarchitecture.client.configuration.rabbitmq.entity.v1;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

import fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.entity.v1.RabbitMQMatchEntityConfiguration;

@Configuration
public class RabbitMQMatchClientConfiguration {

    // publisher    
    @Bean
    public MessageChannel createMatchV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createMatchV1OutboundChannel")
    public AmqpOutboundEndpoint createMatchOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQMatchEntityConfiguration.CREATE_LEAGUE_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
    public MessageChannel updateMatchV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateMatchV1OutboundChannel")
    public AmqpOutboundEndpoint updateMatchOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQMatchEntityConfiguration.UPDATE_LEAGUE_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteMatchV1OutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "deleteMatchV1OutboundChannel")
    public AmqpOutboundEndpoint deleteMatchOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQMatchEntityConfiguration.DELETE_LEAGUE_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
