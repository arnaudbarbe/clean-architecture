package fr.arnaud.cleanarchitecture.client.configuration.rabbitmq.entity.v1;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

import fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.entity.v1.RabbitMQChampionshipEntityConfiguration;

@Configuration
public class RabbitMQChampionshipClientConfiguration {

    
    @Bean
    public MessageChannel createChampionshipV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createChampionshipV1OutboundChannel")
    public AmqpOutboundEndpoint createChampionshipOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQChampionshipEntityConfiguration.CREATE_CHAMPIONSHIP_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
    public MessageChannel updateChampionshipV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateChampionshipV1OutboundChannel")
    public AmqpOutboundEndpoint updateChampionshipOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQChampionshipEntityConfiguration.UPDATE_CHAMPIONSHIP_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteChampionshipV1OutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "deleteChampionshipV1OutboundChannel")
    public AmqpOutboundEndpoint deleteChampionshipOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(RabbitMQChampionshipEntityConfiguration.DELETE_CHAMPIONSHIP_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
