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

import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreateChampionshipHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeleteChampionshipHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdateChampionshipHandler;

@Configuration
public class RabbitMQChampionshipConfiguration {

	private static final String CREATE_CHAMPIONSHIP_QUEUE_NAME_REQUEST = "createChampionshipV1Request";
    
	private static final String UPDATE_CHAMPIONSHIP_QUEUE_NAME_REQUEST = "updateChampionshipV1Request";

	private static final String DELETE_CHAMPIONSHIP_QUEUE_NAME_REQUEST = "deleteChampionshipV1Request";

	private static final String CREATE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST = "createChampionshipV1Request";
	
	private static final String UPDATE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST = "updateChampionshipV1Request";
    
	private static final String DELETE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST = "deleteChampionshipV1Request";

    @Autowired
    MessageCreateChampionshipHandler messageCreateChampionshipHandler;
    
    @Autowired
    MessageUpdateChampionshipHandler messageUpdateChampionshipHandler;
    
    @Autowired
    MessageDeleteChampionshipHandler messageDeleteChampionshipHandler;

    @Bean
    public Declarables championshipDeclarables() {
    	
    	Queue createChampionshipRequestQueue = new Queue(CREATE_CHAMPIONSHIP_QUEUE_NAME_REQUEST, true, false, false);
    	Queue updateChampionshipRequestQueue = new Queue(UPDATE_CHAMPIONSHIP_QUEUE_NAME_REQUEST, true, false, false);
    	Queue deleteChampionshipRequestQueue = new Queue(DELETE_CHAMPIONSHIP_QUEUE_NAME_REQUEST, true, false, false);

    	DirectExchange createChampionshipExchangeRequest = new DirectExchange(CREATE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST, true, false);
    	DirectExchange updateChampionshipExchangeRequest = new DirectExchange(UPDATE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST, true, false);
    	DirectExchange deleteChampionshipExchangeRequest = new DirectExchange(DELETE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST, true, false);
        
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
    
    // consumer
    @Bean
    public IntegrationFlow createChampionshipFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_CHAMPIONSHIP_QUEUE_NAME_REQUEST))
                .handle(this.messageCreateChampionshipHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updateChampionshipFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_CHAMPIONSHIP_QUEUE_NAME_REQUEST))
                .handle(this.messageUpdateChampionshipHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteChampionshipFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_CHAMPIONSHIP_QUEUE_NAME_REQUEST))
                .handle(this.messageDeleteChampionshipHandler::handle)
                .get();
    }
    

    // publisher    
    @Bean
    public MessageChannel createChampionshipV1AsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createChampionshipV1AsyncOutboundChannel")
    public AmqpOutboundEndpoint createChampionshipOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
    public MessageChannel updateChampionshipV1AsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateChampionshipV1AsyncOutboundChannel")
    public AmqpOutboundEndpoint updateChampionshipOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteChampionshipV1AsyncOutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "deleteChampionshipV1AsyncOutboundChannel")
    public AmqpOutboundEndpoint deleteChampionshipOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(DELETE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
