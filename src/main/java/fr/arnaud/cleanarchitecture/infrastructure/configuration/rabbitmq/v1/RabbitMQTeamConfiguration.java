package fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.v1;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreateTeamHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeleteTeamHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdateTeamHandler;

@Configuration
public class RabbitMQTeamConfiguration {

	private static final String CREATE_TEAM_QUEUE_NAME_REQUEST = "createTeamV1Request";
    
	private static final String UPDATE_TEAM_QUEUE_NAME_REQUEST = "updateTeamV1Request";

	private static final String DELETE_TEAM_QUEUE_NAME_REQUEST = "deleteTeamV1Request";

	private static final String CREATE_TEAM_EXCHANGE_NAME_REQUEST = "createTeamV1Request";
	
	private static final String UPDATE_TEAM_EXCHANGE_NAME_REQUEST = "updateTeamV1Request";
    
	private static final String DELETE_TEAM_EXCHANGE_NAME_REQUEST = "deleteTeamV1Request";

    @Autowired
    MessageCreateTeamHandler messageCreateTeamHandler;
    
    @Autowired
    MessageUpdateTeamHandler messageUpdateTeamHandler;
    
    @Autowired
    MessageDeleteTeamHandler messageDeleteTeamHandler;

    @Bean
    public Declarables teamDeclarables() {
    	
    	Queue createTeamRequestQueue = new Queue(CREATE_TEAM_QUEUE_NAME_REQUEST, true, false, false);
    	Queue updateTeamRequestQueue = new Queue(UPDATE_TEAM_QUEUE_NAME_REQUEST, true, false, false);
    	Queue deleteTeamRequestQueue = new Queue(DELETE_TEAM_QUEUE_NAME_REQUEST, true, false, false);

    	DirectExchange createTeamExchangeRequest = new DirectExchange(CREATE_TEAM_EXCHANGE_NAME_REQUEST, true, false);
    	DirectExchange updateTeamExchangeRequest = new DirectExchange(UPDATE_TEAM_EXCHANGE_NAME_REQUEST, true, false);
    	DirectExchange deleteTeamExchangeRequest = new DirectExchange(DELETE_TEAM_EXCHANGE_NAME_REQUEST, true, false);
        
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
    
    // consumer
    @Bean
    public IntegrationFlow createTeamFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_TEAM_QUEUE_NAME_REQUEST))
                .handle(this.messageCreateTeamHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updateTeamFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_TEAM_QUEUE_NAME_REQUEST))
                .handle(this.messageUpdateTeamHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteTeamFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_TEAM_QUEUE_NAME_REQUEST))
                .handle(this.messageDeleteTeamHandler::handle)
                .get();
    }
    

    // publisher    
    @Bean
    public MessageChannel createTeamV1AsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createTeamV1AsyncOutboundChannel")
    public AmqpOutboundEndpoint createTeamOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_TEAM_EXCHANGE_NAME_REQUEST);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
    public MessageChannel updateTeamV1AsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateTeamV1AsyncOutboundChannel")
    public AmqpOutboundEndpoint updateTeamOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_TEAM_EXCHANGE_NAME_REQUEST);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteTeamV1AsyncOutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "deleteTeamV1AsyncOutboundChannel")
    public AmqpOutboundEndpoint deleteTeamOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(DELETE_TEAM_EXCHANGE_NAME_REQUEST);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
