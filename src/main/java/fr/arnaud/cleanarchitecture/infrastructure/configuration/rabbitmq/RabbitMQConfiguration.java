package fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreateLeagueHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreatePlayerHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeleteLeagueHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeletePlayerHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdateLeagueHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdatePlayerHandler;

@Configuration
public class RabbitMQConfiguration {

    @Value("${amqp.broker.host}")
    private String host;

    @Value("${amqp.broker.port}")
    private int port;

    @Value("${amqp.broker.username}")
    private String username;

    @Value("${amqp.broker.password}")
    private String password;
    
    
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
    public Declarables queues() {
        return new Declarables(
                
                new Queue(this.createLeagueQueue, true, false, false),
                
                new Queue(this.updateLeagueQueue, true, false, false),
                
                new Queue(this.deleteLeagueQueue, true, false, false),
                
                new Queue(this.createPlayerQueue, true, false, false),
                
                new Queue(this.updatePlayerQueue, true, false, false),
                
                new Queue(this.deletePlayerQueue, true, false, false)
                
                );
    }

    // consumer

    @Autowired
    MessageCreatePlayerHandler messageCreatePlayerHandler;
    
    @Autowired
    MessageUpdatePlayerHandler messageUpdatePlayerHandler;
    
    @Autowired
    MessageDeletePlayerHandler messageDeletePlayerHandler;
    
    @Autowired
    MessageCreateLeagueHandler messageCreateLeagueHandler;
    
    @Autowired
    MessageUpdateLeagueHandler messageUpdateLeagueHandler;
    
    @Autowired
    MessageDeleteLeagueHandler messageDeleteLeagueHandler;
    

    @Bean
    public IntegrationFlow createLeagueFlow() {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory(), this.createLeagueQueue))
                .handle(this.messageCreateLeagueHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updateLeagueFlow() {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory(), this.updateLeagueQueue))
                .handle(this.messageUpdateLeagueHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteLeagueFlow() {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory(), this.deleteLeagueQueue))
                .handle(this.messageDeleteLeagueHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow createPlayerFlow() {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory(), this.createPlayerQueue))
                .handle(this.messageCreatePlayerHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updatePlayerFlow() {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory(), this.updatePlayerQueue))
                .handle(this.messageUpdatePlayerHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deletePlayerFlow() {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory(), this.deletePlayerQueue))
                .handle(this.messageDeletePlayerHandler::handle)
                .get();
    }
    

    // publisher

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }
}
