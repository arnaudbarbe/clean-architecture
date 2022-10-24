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
public class RabbitMQChampionshipEventConfiguration {

	private static final String CREATE_CHAMPIONSHIP_EVENT_QUEUE_NAME = "event.v1.createChampionship";
    
	private static final String UPDATE_CHAMPIONSHIP_EVENT_QUEUE_NAME = "event.v1.updateChampionship";

	private static final String DELETE_CHAMPIONSHIP_EVENT_QUEUE_NAME = "event.v1.deleteChampionship";

	private static final String CREATE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME = "event.v1.createChampionship";
	
	private static final String UPDATE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME = "event.v1.updateChampionship";
    
	private static final String DELETE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME = "event.v1.deleteChampionship";

    @Bean
    public Declarables championshipEventDeclarables() {
    	
    	Queue createChampionshipRequestQueue = new Queue(CREATE_CHAMPIONSHIP_EVENT_QUEUE_NAME, true, false, false);
    	Queue updateChampionshipRequestQueue = new Queue(UPDATE_CHAMPIONSHIP_EVENT_QUEUE_NAME, true, false, false);
    	Queue deleteChampionshipRequestQueue = new Queue(DELETE_CHAMPIONSHIP_EVENT_QUEUE_NAME, true, false, false);

    	DirectExchange createChampionshipExchangeRequest = new DirectExchange(CREATE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME, true, false);
    	DirectExchange updateChampionshipExchangeRequest = new DirectExchange(UPDATE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME, true, false);
    	DirectExchange deleteChampionshipExchangeRequest = new DirectExchange(DELETE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME, true, false);
        
        return new Declarables(
        		createChampionshipRequestQueue,
        		updateChampionshipRequestQueue,
        		deleteChampionshipRequestQueue,
        		createChampionshipExchangeRequest,
        		updateChampionshipExchangeRequest,
        		deleteChampionshipExchangeRequest,
                BindingBuilder.bind(createChampionshipRequestQueue).to(createChampionshipExchangeRequest).with("#"),
                BindingBuilder.bind(updateChampionshipRequestQueue).to(updateChampionshipExchangeRequest).with("#"),
                BindingBuilder.bind(deleteChampionshipRequestQueue).to(deleteChampionshipExchangeRequest).with("#")
        );
    }
    
    // publisher    
    @Bean
    public MessageChannel createChampionshipEventV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createChampionshipEventV1OutboundChannel")
    public AmqpOutboundEndpoint createChampionshipEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
    public MessageChannel updateChampionshipEventV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateChampionshipEventV1OutboundChannel")
    public AmqpOutboundEndpoint updateChampionshipEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteChampionshipEventV1OutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "deleteChampionshipEventV1OutboundChannel")
    public AmqpOutboundEndpoint deleteChampionshipEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(DELETE_CHAMPIONSHIP_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
