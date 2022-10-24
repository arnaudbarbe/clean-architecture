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
public class RabbitMQTeamEventConfiguration {

	private static final String CREATE_TEAM_EVENT_QUEUE_NAME = "event.v1.createTeam";
    
	private static final String UPDATE_TEAM_EVENT_QUEUE_NAME = "event.v1.updateTeam";

	private static final String DELETE_TEAM_EVENT_QUEUE_NAME = "event.v1.deleteTeam";

	private static final String CREATE_TEAM_EVENT_EXCHANGE_NAME = "event.v1.createTeam";
	
	private static final String UPDATE_TEAM_EVENT_EXCHANGE_NAME = "event.v1.updateTeam";
    
	private static final String DELETE_TEAM_EVENT_EXCHANGE_NAME = "event.v1.deleteTeam";

    @Bean
    public Declarables teamEventDeclarables() {
    	
    	Queue createTeamRequestQueue = new Queue(CREATE_TEAM_EVENT_QUEUE_NAME, true, false, false);
    	Queue updateTeamRequestQueue = new Queue(UPDATE_TEAM_EVENT_QUEUE_NAME, true, false, false);
    	Queue deleteTeamRequestQueue = new Queue(DELETE_TEAM_EVENT_QUEUE_NAME, true, false, false);

    	DirectExchange createTeamExchangeRequest = new DirectExchange(CREATE_TEAM_EVENT_EXCHANGE_NAME, true, false);
    	DirectExchange updateTeamExchangeRequest = new DirectExchange(UPDATE_TEAM_EVENT_EXCHANGE_NAME, true, false);
    	DirectExchange deleteTeamExchangeRequest = new DirectExchange(DELETE_TEAM_EVENT_EXCHANGE_NAME, true, false);
        
        return new Declarables(
        		createTeamRequestQueue,
        		updateTeamRequestQueue,
        		deleteTeamRequestQueue,
        		createTeamExchangeRequest,
        		updateTeamExchangeRequest,
        		deleteTeamExchangeRequest,
                BindingBuilder.bind(createTeamRequestQueue).to(createTeamExchangeRequest).with("#"),
                BindingBuilder.bind(updateTeamRequestQueue).to(updateTeamExchangeRequest).with("#"),
                BindingBuilder.bind(deleteTeamRequestQueue).to(deleteTeamExchangeRequest).with("#")
        );
    }
    
    // publisher    
    @Bean
    public MessageChannel createTeamEventV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createTeamEventV1OutboundChannel")
    public AmqpOutboundEndpoint createTeamEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_TEAM_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
    public MessageChannel updateTeamEventV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateTeamEventV1OutboundChannel")
    public AmqpOutboundEndpoint updateTeamEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_TEAM_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteTeamEventV1OutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "deleteTeamEventV1OutboundChannel")
    public AmqpOutboundEndpoint deleteTeamEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(DELETE_TEAM_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
