package fr.arnaud.cleanarchitecture.client.configuration.rabbitmq.v1.channel;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

import fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.v1.listener.RabbitMQTeamEntityListener;

@Configuration
public class RabbitMQTeamChannel {

    // publisher    
    @Bean
    public MessageChannel createTeamV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createTeamV1OutboundChannel")
    public AmqpOutboundEndpoint createTeamOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQTeamEntityListener.CREATE_TEAM_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
    public MessageChannel updateTeamV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateTeamV1OutboundChannel")
    public AmqpOutboundEndpoint updateTeamOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQTeamEntityListener.UPDATE_TEAM_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteTeamV1OutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "deleteTeamV1OutboundChannel")
    public AmqpOutboundEndpoint deleteTeamOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQTeamEntityListener.DELETE_TEAM_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
