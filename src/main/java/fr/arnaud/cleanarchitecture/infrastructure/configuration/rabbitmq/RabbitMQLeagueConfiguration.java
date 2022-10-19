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

import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreateLeagueHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeleteLeagueHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdateLeagueHandler;

@Configuration
public class RabbitMQLeagueConfiguration {

	private static final String CREATE_LEAGUE_QUEUE_NAME = "createLeague";
    
	private static final String UPDATE_LEAGUE_QUEUE_NAME = "updateLeague";

	private static final String DELETE_LEAGUE_QUEUE_NAME = "deleteLeague";

	private static final String CREATE_LEAGUE_EXCHANGE_NAME = "createLeague";
 
	private static final String UPDATE_LEAGUE_EXCHANGE_NAME = "updateLeague";
    
	private static final String DELETE_LEAGUE_EXCHANGE_NAME = "deleteLeague";

    @Autowired
    MessageCreateLeagueHandler messageCreateLeagueHandler;
    
    @Autowired
    MessageUpdateLeagueHandler messageUpdateLeagueHandler;
    
    @Autowired
    MessageDeleteLeagueHandler messageDeleteLeagueHandler;

    @Bean
    public Declarables leagueDeclarables() {
    	
    	Queue createLeagueQueue = new Queue(CREATE_LEAGUE_QUEUE_NAME, true, false, false);
    	Queue updateLeagueQueue = new Queue(UPDATE_LEAGUE_QUEUE_NAME, true, false, false);
    	Queue deleteLeagueQueue = new Queue(DELETE_LEAGUE_QUEUE_NAME, true, false, false);

    	DirectExchange createLeagueExchange = new DirectExchange(CREATE_LEAGUE_EXCHANGE_NAME, true, false);
    	DirectExchange updateLeagueExchange = new DirectExchange(UPDATE_LEAGUE_EXCHANGE_NAME, true, false);
    	DirectExchange deleteLeagueExchange = new DirectExchange(DELETE_LEAGUE_EXCHANGE_NAME, true, false);

        BindingBuilder.bind(createLeagueQueue).to(createLeagueExchange);
        BindingBuilder.bind(updateLeagueQueue).to(updateLeagueExchange);
        BindingBuilder.bind(deleteLeagueQueue).to(deleteLeagueExchange);
        
        return new Declarables(
        		createLeagueQueue,
        		updateLeagueQueue,
        		deleteLeagueQueue,
        		createLeagueExchange,
        		updateLeagueExchange,
        		deleteLeagueExchange,
                BindingBuilder.bind(createLeagueQueue).to(createLeagueExchange).with("#"),
                BindingBuilder.bind(updateLeagueQueue).to(updateLeagueExchange).with("#"),
                BindingBuilder.bind(deleteLeagueQueue).to(deleteLeagueExchange).with("#")
        );
    }
    
    // consumer
    @Bean
    public IntegrationFlow createLeagueFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_LEAGUE_QUEUE_NAME))
                .handle(this.messageCreateLeagueHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updateLeagueFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_LEAGUE_QUEUE_NAME))
                .handle(this.messageUpdateLeagueHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteLeagueFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_LEAGUE_QUEUE_NAME))
                .handle(this.messageDeleteLeagueHandler::handle)
                .get();
    }
    

    // publisher    
    @Bean
    public MessageChannel createLeagueAsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createLeagueAsyncOutboundChannel")
    public AmqpOutboundEndpoint createLeagueOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_LEAGUE_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel updateLeagueAsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateLeagueAsyncOutboundChannel")
    public AmqpOutboundEndpoint updateLeagueOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_LEAGUE_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteLeagueAsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "deleteLeagueAsyncOutboundChannel")
    public AmqpOutboundEndpoint deleteLeagueOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(DELETE_LEAGUE_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
