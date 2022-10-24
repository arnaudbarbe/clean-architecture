package fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.event.v1;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class RabbitMQLeagueEventConfiguration {

	private static final String CREATE_LEAGUE_EVENT_QUEUE_NAME = "event.v1.createLeague";
    
	private static final String UPDATE_LEAGUE_EVENT_QUEUE_NAME = "event.v1.updateLeague";

	private static final String DELETE_LEAGUE_EVENT_QUEUE_NAME = "event.v1.deleteLeague";

	private static final String CREATE_LEAGUE_EVENT_EXCHANGE_NAME = "event.v1.createLeague";
	
	private static final String UPDATE_LEAGUE_EVENT_EXCHANGE_NAME = "event.v1.updateLeague";
    
	private static final String DELETE_LEAGUE_EVENT_EXCHANGE_NAME = "event.v1.deleteLeague";

    @Bean
    public Declarables leagueEventDeclarables() {
    	
    	Queue createLeagueRequestQueue = new Queue(CREATE_LEAGUE_EVENT_QUEUE_NAME, true, false, false);
    	Queue updateLeagueRequestQueue = new Queue(UPDATE_LEAGUE_EVENT_QUEUE_NAME, true, false, false);
    	Queue deleteLeagueRequestQueue = new Queue(DELETE_LEAGUE_EVENT_QUEUE_NAME, true, false, false);

    	DirectExchange createLeagueExchangeRequest = new DirectExchange(CREATE_LEAGUE_EVENT_EXCHANGE_NAME, true, false);
    	DirectExchange updateLeagueExchangeRequest = new DirectExchange(UPDATE_LEAGUE_EVENT_EXCHANGE_NAME, true, false);
    	DirectExchange deleteLeagueExchangeRequest = new DirectExchange(DELETE_LEAGUE_EVENT_EXCHANGE_NAME, true, false);
        
        return new Declarables(
        		createLeagueRequestQueue,
        		updateLeagueRequestQueue,
        		deleteLeagueRequestQueue,
        		createLeagueExchangeRequest,
        		updateLeagueExchangeRequest,
        		deleteLeagueExchangeRequest,
                BindingBuilder.bind(createLeagueRequestQueue).to(createLeagueExchangeRequest).with("#"),
                BindingBuilder.bind(updateLeagueRequestQueue).to(updateLeagueExchangeRequest).with("#"),
                BindingBuilder.bind(deleteLeagueRequestQueue).to(deleteLeagueExchangeRequest).with("#")
        );
    }
    
    // publisher    
    @Bean
    public MessageChannel createLeagueEventV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createLeagueEventV1OutboundChannel")
    public AmqpOutboundEndpoint createLeagueEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_LEAGUE_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
    public MessageChannel updateLeagueEventV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateLeagueEventV1OutboundChannel")
    public AmqpOutboundEndpoint updateLeagueEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_LEAGUE_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteLeagueEventV1OutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "deleteLeagueEventV1OutboundChannel")
    public AmqpOutboundEndpoint deleteLeagueEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(DELETE_LEAGUE_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
