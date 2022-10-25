package fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.entity.v1;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageCreateTeamHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageDeleteTeamHandler;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1.MessageUpdateTeamHandler;

@Configuration
public class RabbitMQTeamEntityConfiguration {

	public static final String CREATE_TEAM_QUEUE_NAME = "entity.v1.team.create";
    
	public static final String UPDATE_TEAM_QUEUE_NAME = "entity.v1.team.update";

	public static final String DELETE_TEAM_QUEUE_NAME = "entity.v1.team.delete";

	public static final String CREATE_TEAM_EXCHANGE_NAME = "entity.v1.team.create";
	
	public static final String UPDATE_TEAM_EXCHANGE_NAME = "entity.v1.team.update";
    
	public static final String DELETE_TEAM_EXCHANGE_NAME = "entity.v1.team.delete";

    @Autowired
    MessageCreateTeamHandler messageCreateTeamHandler;
    
    @Autowired
    MessageUpdateTeamHandler messageUpdateTeamHandler;
    
    @Autowired
    MessageDeleteTeamHandler messageDeleteTeamHandler;

    @Bean
    public Declarables teamDeclarables() {
    	
    	Queue createTeamRequestQueue = new Queue(CREATE_TEAM_QUEUE_NAME, true, false, false);
    	Queue updateTeamRequestQueue = new Queue(UPDATE_TEAM_QUEUE_NAME, true, false, false);
    	Queue deleteTeamRequestQueue = new Queue(DELETE_TEAM_QUEUE_NAME, true, false, false);

    	DirectExchange createTeamExchange = new DirectExchange(CREATE_TEAM_EXCHANGE_NAME, true, false);
    	DirectExchange updateTeamExchange = new DirectExchange(UPDATE_TEAM_EXCHANGE_NAME, true, false);
    	DirectExchange deleteTeamExchange = new DirectExchange(DELETE_TEAM_EXCHANGE_NAME, true, false);
        
        return new Declarables(
        		createTeamRequestQueue,
        		updateTeamRequestQueue,
        		deleteTeamRequestQueue,
        		createTeamExchange,
        		updateTeamExchange,
        		deleteTeamExchange,
                BindingBuilder.bind(createTeamRequestQueue).to(createTeamExchange).with("#"),
                BindingBuilder.bind(updateTeamRequestQueue).to(updateTeamExchange).with("#"),
                BindingBuilder.bind(deleteTeamRequestQueue).to(deleteTeamExchange).with("#")
        );
    }
    
    // consumer
    @Bean
    public IntegrationFlow createTeamFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, CREATE_TEAM_QUEUE_NAME))
                .handle(this.messageCreateTeamHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow updateTeamFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, UPDATE_TEAM_QUEUE_NAME))
                .handle(this.messageUpdateTeamHandler::handle)
                .get();
    }
    

    @Bean
    public IntegrationFlow deleteTeamFlow(final ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, DELETE_TEAM_QUEUE_NAME))
                .handle(this.messageDeleteTeamHandler::handle)
                .get();
    }
}
