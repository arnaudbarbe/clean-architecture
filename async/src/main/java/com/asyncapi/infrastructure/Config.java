package com.asyncapi.infrastructure;

import com.asyncapi.service.MessageHandlerService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

@Configuration
public class Config {

    @Value("${amqp.broker.host}")
    private String host;

    @Value("${amqp.broker.port}")
    private int port;

    @Value("${amqp.broker.username}")
    private String username;

    @Value("${amqp.broker.password}")
    private String password;

    
    @Value("${amqp.exchange.createLeague}")
    private String createLeagueExchange;

    
    @Value("${amqp.exchange.updateLeague}")
    private String updateLeagueExchange;

    
    @Value("${amqp.exchange.deleteLeague}")
    private String deleteLeagueExchange;

    
    @Value("${amqp.exchange.createPlayer}")
    private String createPlayerExchange;

    
    @Value("${amqp.exchange.updatePlayer}")
    private String updatePlayerExchange;

    
    @Value("${amqp.exchange.deletePlayer}")
    private String deletePlayerExchange;

    
    
    @Value("${amqp.queue.createLeague}")
    private String createLeagueQueue;

    
    @Value("${amqp.queue.updateLeague}")
    private String updateLeagueQueue;

    
    @Value("${amqp.queue.deleteLeague}")
    private String deleteLeagueQueue;

    
    @Value("${amqp.queue.createPlayer}")
    private String createPlayerQueue;

    
    @Value("${amqp.queue.updatePlayer}")
    private String updatePlayerQueue;

    
    @Value("${amqp.queue.deletePlayer}")
    private String deletePlayerQueue;

    

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setPort(port);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public Declarables exchanges() {
        return new Declarables(
                
                new TopicExchange(createLeagueExchange, true, false),
                
                new TopicExchange(updateLeagueExchange, true, false),
                
                new TopicExchange(deleteLeagueExchange, true, false),
                
                new TopicExchange(createPlayerExchange, true, false),
                
                new TopicExchange(updatePlayerExchange, true, false),
                
                new TopicExchange(deletePlayerExchange, true, false)
                
                );
    }

    @Bean
    public Declarables queues() {
        return new Declarables(
                
                new Queue(createLeagueQueue, true, false, false),
                
                new Queue(updateLeagueQueue, true, false, false),
                
                new Queue(deleteLeagueQueue, true, false, false),
                
                new Queue(createPlayerQueue, true, false, false),
                
                new Queue(updatePlayerQueue, true, false, false),
                
                new Queue(deletePlayerQueue, true, false, false)
                
                );
    }

    // consumer

    @Autowired
    MessageHandlerService messageHandlerService;
    

    @Bean
    public IntegrationFlow createLeagueFlow() {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory(), createLeagueQueue))
                .handle(messageHandlerService::handleCreateLeague)
                .get();
    }
    

    @Bean
    public IntegrationFlow updateLeagueFlow() {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory(), updateLeagueQueue))
                .handle(messageHandlerService::handleUpdateLeague)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteLeagueFlow() {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory(), deleteLeagueQueue))
                .handle(messageHandlerService::handleDeleteLeague)
                .get();
    }
    

    @Bean
    public IntegrationFlow createPlayerFlow() {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory(), createPlayerQueue))
                .handle(messageHandlerService::handleCreatePlayer)
                .get();
    }
    

    @Bean
    public IntegrationFlow updatePlayerFlow() {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory(), updatePlayerQueue))
                .handle(messageHandlerService::handleUpdatePlayer)
                .get();
    }
    

    @Bean
    public IntegrationFlow deletePlayerFlow() {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory(), deletePlayerQueue))
                .handle(messageHandlerService::handleDeletePlayer)
                .get();
    }
    

    // publisher

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }
    

    @Bean
    public MessageChannel createLeagueSubOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createLeagueSubOutboundChannel")
    public AmqpOutboundEndpoint createLeagueOutbound(AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(createLeagueExchange);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel updateLeagueSubOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updateLeagueSubOutboundChannel")
    public AmqpOutboundEndpoint updateLeagueOutbound(AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(updateLeagueExchange);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteLeagueSubOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "deleteLeagueSubOutboundChannel")
    public AmqpOutboundEndpoint deleteLeagueOutbound(AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(deleteLeagueExchange);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel createPlayerSubOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createPlayerSubOutboundChannel")
    public AmqpOutboundEndpoint createPlayerOutbound(AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(createPlayerExchange);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel updatePlayerSubOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "updatePlayerSubOutboundChannel")
    public AmqpOutboundEndpoint updatePlayerOutbound(AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(updatePlayerExchange);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deletePlayerSubOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "deletePlayerSubOutboundChannel")
    public AmqpOutboundEndpoint deletePlayerOutbound(AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(deletePlayerExchange);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
}
