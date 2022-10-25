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
public class RabbitMQSeasonEventConfiguration {

    private static final String CREATE_SEASON_EVENT_EXCHANGE_NAME = "event.v1.createSeason";

    private static final String UPDATE_SEASON_EVENT_EXCHANGE_NAME = "event.v1.updateSeason";

    private static final String DELETE_SEASON_EVENT_EXCHANGE_NAME = "event.v1.deleteSeason";

    @Bean
    public Declarables seasonEventDeclarables() {
    	
    	DirectExchange createSeasonExchange = new DirectExchange(CREATE_SEASON_EVENT_EXCHANGE_NAME, true, false);
    	DirectExchange updateSeasonExchange = new DirectExchange(UPDATE_SEASON_EVENT_EXCHANGE_NAME, true, false);
    	DirectExchange deleteSeasonExchange = new DirectExchange(DELETE_SEASON_EVENT_EXCHANGE_NAME, true, false);

        return new Declarables(
        		createSeasonExchange,
        		updateSeasonExchange,
        		deleteSeasonExchange
        );
    }
    
    // publisher   
    @Bean
    public MessageChannel createSeasonEventV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createSeasonEventV1OutboundChannel")
    public AmqpOutboundEndpoint createSeasonEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_SEASON_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel updateSeasonEventV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateSeasonEventV1OutboundChannel")
    public AmqpOutboundEndpoint updateSeasonEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_SEASON_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteSeasonEventV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "deleteSeasonEventV1OutboundChannel")
    public AmqpOutboundEndpoint deleteSeasonEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(DELETE_SEASON_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
