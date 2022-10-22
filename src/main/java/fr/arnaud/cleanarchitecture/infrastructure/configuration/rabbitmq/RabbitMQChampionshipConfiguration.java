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

import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreateChampionshipHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeleteChampionshipHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdateChampionshipHandler;

@Configuration
public class RabbitMQChampionshipConfiguration {

	private static final String CREATE_CHAMPIONSHIP_QUEUE_NAME_REQUEST_V1 = "createChampionshipRequestV1";
    
	private static final String UPDATE_CHAMPIONSHIP_QUEUE_NAME_REQUEST_V1 = "updateChampionshipRequestV1";

	private static final String DELETE_CHAMPIONSHIP_QUEUE_NAME_REQUEST_V1 = "deleteChampionshipRequestV1";

	private static final String CREATE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST_V1 = "createChampionshipRequestV1";
	
	private static final String UPDATE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST_V1 = "updateChampionshipRequestV1";
    
	private static final String DELETE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST_V1 = "deleteChampionshipRequestV1";

    @Autowired
    MessageCreateChampionshipHandler messageCreateChampionshipHandlerV1;
    
    @Autowired
    MessageUpdateChampionshipHandler messageUpdateChampionshipHandlerV1;
    
    @Autowired
    MessageDeleteChampionshipHandler messageDeleteChampionshipHandlerV1;

    @Bean
    public Declarables championshipDeclarables() {
    	
    	Queue createChampionshipRequestQueueV1 = new Queue(CREATE_CHAMPIONSHIP_QUEUE_NAME_REQUEST_V1, true, false, false);
    	Queue updateChampionshipRequestQueueV1 = new Queue(UPDATE_CHAMPIONSHIP_QUEUE_NAME_REQUEST_V1, true, false, false);
    	Queue deleteChampionshipRequestQueueV1 = new Queue(DELETE_CHAMPIONSHIP_QUEUE_NAME_REQUEST_V1, true, false, false);

    	DirectExchange createChampionshipExchangeRequestV1 = new DirectExchange(CREATE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST_V1, true, false);
    	DirectExchange updateChampionshipExchangeRequestV1 = new DirectExchange(UPDATE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST_V1, true, false);
    	DirectExchange deleteChampionshipExchangeRequestV1 = new DirectExchange(DELETE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST_V1, true, false);
        
        return new Declarables(
        		createChampionshipRequestQueueV1,
        		updateChampionshipRequestQueueV1,
        		deleteChampionshipRequestQueueV1,
        		createChampionshipExchangeRequestV1,
        		updateChampionshipExchangeRequestV1,
        		deleteChampionshipExchangeRequestV1,
                BindingBuilder.bind(createChampionshipRequestQueueV1).to(createChampionshipExchangeRequestV1).with("#"),
                BindingBuilder.bind(updateChampionshipRequestQueueV1).to(updateChampionshipExchangeRequestV1).with("#"),
                BindingBuilder.bind(deleteChampionshipRequestQueueV1).to(deleteChampionshipExchangeRequestV1).with("#")
        );
    }
    
    // consumer
    @Bean
    public IntegrationFlow createChampionshipFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_CHAMPIONSHIP_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageCreateChampionshipHandlerV1::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updateChampionshipFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_CHAMPIONSHIP_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageUpdateChampionshipHandlerV1::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteChampionshipFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_CHAMPIONSHIP_QUEUE_NAME_REQUEST_V1))
                .handle(this.messageDeleteChampionshipHandlerV1::handle)
                .get();
    }
    

    // publisher    
    @Bean
    public MessageChannel createChampionshipAsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createChampionshipAsyncOutboundChannel")
    public AmqpOutboundEndpoint createChampionshipOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST_V1);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
    public MessageChannel updateChampionshipAsyncOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateChampionshipAsyncOutboundChannel")
    public AmqpOutboundEndpoint updateChampionshipOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(UPDATE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST_V1);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteChampionshipAsyncOutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "deleteChampionshipAsyncOutboundChannel")
    public AmqpOutboundEndpoint deleteChampionshipOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(DELETE_CHAMPIONSHIP_EXCHANGE_NAME_REQUEST_V1);
        outbound.setRoutingKey("#");
        return outbound;
    }
}
