package com.asyncapi.infrastructure;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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

import com.asyncapi.service.MessageHandlerService;

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

    
    @Value("${amqp.exchange.deleteLeague}")
    private String deleteLeagueExchange;

    
    
    @Value("${amqp.queue.createLeague}")
    private String createLeagueQueue;

    
    @Value("${amqp.queue.deleteLeague}")
    private String deleteLeagueQueue;

    

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(this.host);
        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        connectionFactory.setPort(this.port);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public Declarables exchanges() {
        return new Declarables(
                
                new TopicExchange(this.createLeagueExchange, true, false),
                
                new TopicExchange(this.deleteLeagueExchange, true, false)
                
                );
    }

    @Bean
    public Declarables queues() {
        return new Declarables(
                
                new Queue(this.createLeagueQueue, true, false, false),
                
                new Queue(this.deleteLeagueQueue, true, false, false)
                
                );
    }

    // consumer

    @Autowired
    MessageHandlerService messageHandlerService;
    

    @Bean
    public IntegrationFlow createLeagueFlow() {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory(), this.createLeagueQueue))
                .handle(this.messageHandlerService::handleCreateLeague)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteLeagueFlow() {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory(), this.deleteLeagueQueue))
                .handle(this.messageHandlerService::handleDeleteLeague)
                .get();
    }
    

    // publisher

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }
    

    @Bean
    public MessageChannel createLeagueSubOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createLeagueSubOutboundChannel")
    public AmqpOutboundEndpoint createLeagueOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(this.createLeagueExchange);
        outbound.setRoutingKey("#");
        return outbound;
    }
    

    @Bean
    public MessageChannel deleteLeagueSubOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "deleteLeagueSubOutboundChannel")
    public AmqpOutboundEndpoint deleteLeagueOutbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(this.deleteLeagueExchange);
        outbound.setRoutingKey("#");
        return outbound;
    }
    
}
