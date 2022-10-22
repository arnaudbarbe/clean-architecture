package fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq;

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

	private static final String CREATE_TEAM_QUEUE_NAME_REQUEST_V1 = "createTeamRequestV1";
    
	private static final String UPDATE_TEAM_QUEUE_NAME_REQUEST_V1 = "updateTeamRequestV1";

	private static final String DELETE_TEAM_QUEUE_NAME_REQUEST_V1 = "deleteTeamRequestV1";

	private static final String CREATE_TEAM_EXCHANGE_NAME_REQUEST_V1 = "createTeamRequestV1";
	
	private static final String UPDATE_TEAM_EXCHANGE_NAME_REQUEST_V1 = "updateTeamRequestV1";
    
	private static final String DELETE_TEAM_EXCHANGE_NAME_REQUEST_V1 = "deleteTeamRequestV1";

    @Autowired
    MessageCreateTeamHandler messageCreateTeamHandlerV1;
    
    @Autowired
    MessageUpdateTeamHandler messageUpdateTeamHandlerV1;
    
    @Autowired
    MessageDeleteTeamHandler messageDeleteTeamHandlerV1;

    @Bean
    public Declarables teamDeclarables() {
    	
    	Queue createTeamRequestQueueV1 = new Queue(CREATE_TEAM_QUEUE_NAME_REQUEST_V1, true, false, false);
    	Queue updateTeamRequestQueueV1 = new Queue(UPDATE_TEAM_QUEUE_NAME_REQUEST_V1, true, false, false);
    	Queue deleteTeamRequestQueueV1 = new Queue(DELETE_TEAM_QUEUE_NAME_REQUEST_V1, true, false, false);

    	DirectExchange createTeamExchangeRequestV1 = new DirectExchange(CREATE_TEAM_EXCHANGE_NAME_REQUEST_V1, true, false);
    	DirectExchange updateTeamExchangeRequestV1 = new DirectExchange(UPDATE_TEAM_EXCHANGE_NAME_REQUEST_V1, true, false);
    	DirectExchange deleteTeamExchangeRequestV1 = new DirectExchange(DELETE_TEAM_EXCHANGE_NAME_REQUEST_V1, true, false);
        
        return new Declarables(
        		createTeamRequestQueueV1,
        		updateTeamRequestQueueV1,
        		deleteTeamRequestQueueV1,
        		createTeamExchangeRequestV1,
        		updateTeamExchangeRequestV1,
        		deleteTeamExchangeRequestV1,
                BindingBuilder.bind(createTeamRequestQueueV1).to(createTeamExchangeRequestV1).with("#"),
                BindingBuilder.bind(updateTeamRequestQueueV1).to(updateTeamExchangeRequestV1).with("#"),
                BindingBuilder.bind(deleteTeamRequestQueueV1).to(deleteTeamExchangeRequestV1).with("#")
        );
    }
    
    // consumer
    @Bean
    public IntegrationFlow createTeamFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_TEAM_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageCreateTeamHandlerV1::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updateTeamFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_TEAM_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageUpdateTeamHandlerV1::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteTeamFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_TEAM_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageDeleteTeamHandlerV1::handle)
                .get();
    }
    

    // publisher    
    @Bean
    public MessageChannel createTeamAsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createTeamAsyncOutboundChannel")
    public AmqpOutboundEndpoint createTeamOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_TEAM_EXCHANGE_NAME_REQUEST_V1);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
    public MessageChannel updateTeamAsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateTeamAsyncOutboundChannel")
    public AmqpOutboundEndpoint updateTeamOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_TEAM_EXCHANGE_NAME_REQUEST_V1);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteTeamAsyncOutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "deleteTeamAsyncOutboundChannel")
    public AmqpOutboundEndpoint deleteTeamOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(DELETE_TEAM_EXCHANGE_NAME_REQUEST_V1);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
