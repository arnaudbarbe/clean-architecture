# clean-architecture

Personal implementation of a clean architecture

Table of contents

&nbsp;&nbsp;1.1. [folders organization](#folders-organization)  
&nbsp;&nbsp;1.2. [Running tests](#running-tests)  
2. [Features added](#features-added)  
&nbsp;&nbsp;2.1. [event sourcing](#event-sourcing)  
&nbsp;&nbsp;2.2.[persistence](#persistence)  
&nbsp;&nbsp;2.3.[Swagger](#swagger)  
&nbsp;&nbsp;2.4.[HATEOAS](#hateoas)  
&nbsp;&nbsp;2.5.[security](#security)  
&nbsp;&nbsp;2.6.[Code coverage](#code-coverage)  
3.[coming soon](#coming-soon)  
&nbsp;&nbsp;3.1.[resilience](#resilience)  
&nbsp;&nbsp;3.2.[Grafana & prometheus](#grafana--prometheus)  
&nbsp;&nbsp;3.3.[SAGA or LRA support](#saga-or-lra-support)  
&nbsp;&nbsp;3.4.[Minikube](#minikube)  


## folders organization
* client (codegen client based on Swagger json api descriptor)
* core (business part)
  * exception 
  * model (Entities, Values object and aggregate)
  * repository (interfaces to storage, should be implemented by infrastructure)
  * service (Business rules)
* infrastructure (technical part)
  * configuration (configuration for each framework/persistence/etc...)
    * advice (Exception processor)
    * cassandra 
    * cors
    * eventdriven (aspect classes around services classes to throw messages on create/update/delete)
    * mongo
    * postgres
    * rabbitmq (event and service exchange/queue configurations)
    * security
    * service 
    * swagger
  * delivery (entry point from other system)
    * consumer (MOM consumers/handlers)
    * controller (REST controller)
    * task (cron task to execute scheduled process)
    * dto (versionned Data Transfert Object used in consumer and controller classes)
  * gateway (entry point to other system)
  * persistence (repositories for each storage system)
  * publisher (MOM client to create/update/delete entities)
  * service (only technical service)
## Running tests

You need to start containers in src/main/resources/dockercompose/docker-compose.yml before running test or start the spring boot server

start docker instances
~~~~
docker-compose -f docker-compose.yml up -d
~~~~

remove docker instances **AND VOLUME (destroy datas)**
~~~~
docker-compose -f docker-compose.yml down -v
~~~~

## Features added

### REST API and Async

2 options are available to create/update/delete object.
- REST API access via http requests (see [swagger](#swagger) )
- Async (RabbitMQ) via Publishers  
Example 
[LeaguePublisher.java](../main/src/main/java/fr/arnaud/cleanarchitecture/client/configuration/rabbitmq/v1/publisher/LeaguePublisher.java)
~~~~
package fr.arnaud.cleanarchitecture.client.configuration.rabbitmq.v1.publisher;
...
@MessagingGateway
public interface LeaguePublisher {

    @Gateway(requestChannel = "createLeagueV1OutboundChannel")
    void createLeague(LeagueDto league);
    
    @Gateway(requestChannel = "updateLeagueV1OutboundChannel")
    void updateLeague(LeagueDto league);

    @Gateway(requestChannel = "deleteLeagueV1OutboundChannel")
    void deleteLeague(UUID id);
}
~~~~
Channel declaration in 

### event sourcing

events are sent on create/update/delete operations.
We used Aspect annotation in infrastructure/configuration/eventdriven/aspect

see below an example for League object. Whenever the fr.arnaud.cleanarchitecture.core.service.league.DomainLeagueService.createLeague() method is called, eventAfterCreateLeague() is triggered and a message with the dto is sent.

[LeagueAspect.java](../main/src/main/java/fr/arnaud/cleanarchitecture/infrastructure/configuration/eventdriven/aspect/LeagueAspect.java)
~~~~
package fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.aspect;
...
@Aspect
@Component
public class LeagueAspect {
    
    private final LeagueEventPublisher leagueEventPublisher;
	
    @Autowired
    public LeagueAspect(final LeagueEventPublisher leagueEventPublisher) {
        this.leagueEventPublisher = leagueEventPublisher;
    }
	
    @After("execution(* fr.arnaud.cleanarchitecture.core.service.league.DomainLeagueService.createLeague(..)) && args(league)")
    public void eventAfterCreateLeague(final JoinPoint joinPoint, final League league) {

    	Event<LeagueDto> event = 
    			new Event<>(
    					Event.StandardStatus.CREATED.name(), 
    					LeagueDto.fromEntity(league));

    	this.leagueEventPublisher.createLeagueEvent(event);
    }
...
}
~~~~
Publisher
[LeagueEventPublisherService.java](../main/src/main/java/fr/arnaud/cleanarchitecture/infrastructure/publisher/event/v1/LeagueEventPublisherService.java)
~~~~
package fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.publisher.v1;
...
@MessagingGateway
public interface LeagueEventPublisherService {

    @Gateway(requestChannel = "createLeagueEventV1OutboundChannel")
    void createLeagueEvent(LeagueDto league);
    
    @Gateway(requestChannel = "updateLeagueEventV1OutboundChannel")
    void updateLeagueEvent(LeagueDto league);

    @Gateway(requestChannel = "deleteLeagueEventV1OutboundChannel")
    void deleteLeagueEvent(UUID id);
}
~~~~
Channel declaration in [RabbitMQLeagueEventConfiguration.java](../main/src/main/java/fr/arnaud/cleanarchitecture/infrastructure/configuration/rabbitmq/event/v1/RabbitMQLeagueEventConfiguration.java)

~~~~
    @Bean
    public MessageChannel createLeagueEventV1OutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createLeagueEventV1OutboundChannel")
    public AmqpOutboundEndpoint createLeagueEventV1Outbound(final AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExchangeName(CREATE_LEAGUE_EVENT_EXCHANGE_NAME);
        outbound.setRoutingKey("#");
        return outbound;
    }
~~~~
### persistence

I'm using postgres, mongodb and cassandra to persist data.  
This is just for fun, don't try this at home.

In core fr/arnaud/cleanarchitecture/core/repository contains interfaces implemented in infrastructure part.
Example 

League repository interface
[LeagueRepository.java](../main/src/main/java/fr/arnaud/cleanarchitecture/core/repository/LeagueRepository.java)

The repository implementation
[PostgresDbLeagueRepository.java](../main/src/main/java/fr/arnaud/cleanarchitecture/infrastructure/persistence/postgres/league/PostgresDbLeagueRepository.java)

The postgres configuration
[PostgresConfiguration.java](../main/src/main/java/fr/arnaud/cleanarchitecture/infrastructure/configuration/postgres/PostgresConfiguration.java)

### Swagger

Swagger is available under
http://localhost:8090/swagger-ui/index.html  
[SwaggerConfiguration.java](../main/src/main/java/fr/arnaud/cleanarchitecture/infrastructure/configuration/swagger/SwaggerConfiguration.java)
~~~~
package fr.arnaud.cleanarchitecture.infrastructure.configuration.swagger;
...
@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI api() {
		return new OpenAPI()
			.components(new Components().addSecuritySchemes("accessToken",
				new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").in(SecurityScheme.In.HEADER).name("Authorization")))
				.info(new Info().title("Test Server").description("This is the API description to the server").version("1.0")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}
}
~~~~


### HATEOAS

HATEOAS return link to delete, update, get and getAll on getOne and getAll operations

Each controller return XXXModel object whereas XXXDto are used as input object.
XXXModel extends RepresentationModel<ChampionshipModel> that provided HATEOAS features.

Example with League object  

Entity [League](../main/src/main/java/fr/arnaud/cleanarchitecture/infrastructure/delivery/dto/v1/LeagueDto.java) is used in core part
	
[LeagueDto](../main/src/main/java/fr/arnaud/cleanarchitecture/infrastructure/delivery/dto/v1/LeagueDto.java) is used as input record
~~~~
public ResponseEntity<Void> updateLeague(@PathVariable final UUID leagueId, @RequestBody final LeagueDto league) {
this.leagueService.updateLeague(leagueId, league.toEntity());
	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
}
~~~~
[LeagueModel](../main/src/main/java/fr/arnaud/cleanarchitecture/infrastructure/delivery/controller/v1/model/LeagueModel.java) are enrich in Controller and contains link to delete, update, get and getAll methods  
~~~~
public ResponseEntity<LeagueModel> getLeague(@PathVariable final UUID leagueId) {
	League entity = this.leagueService.getLeague(leagueId);
	if (entity == null) {
		return null;
	} else {

		LeagueModel model = LeagueModel.fromEntity(entity)
				.add(getSelfLink(entity.getId()))
				.add(getCreateLink())
				.add(getUpdateLink(entity.getId()))
				.add(getDeleteLink(entity.getId()))
				.add(getGetAllLink());

		return ResponseEntity
		      .status(HttpStatus.OK)
		      .body(model);
	}
}
~~~~

JSON return content on a getLeague method with id 515c419a-f59e-4814-a05a-cab9c09a20b9.
~~~~
{
  "id":"515c419a-f59e-4814-a05a-cab9c09a20b9",
  "name":"FFB",
  "_links":{
    "self":{"href":"http://localhost/v1/leagues/515c419a-f59e-4814-a05a-cab9c09a20b9"},
    "create":{"href":"http://localhost/v1/leagues"},
    "update":{"href":"http://localhost/v1/leagues/515c419a-f59e-4814-a05a-cab9c09a20b9"},
    "delete":{"href":"http://localhost/v1/leagues/515c419a-f59e-4814-a05a-cab9c09a20b9"},
    "getAll":{"href":"http://localhost/v1/leagues"}
  }
}
~~~~
### security 

An instance of Keycloak was added in docker compose file

admin interface is available at http://localhost:8080/

* user : admin
* password : admin

2 users exist
- user: user1, password: user1, role: USER
- user: admin1, password: admin1, role: ADMIN

Keycloack is used in a different way that we could see in some examples.
First I don't want to use keycloak login screen, so a method to get JWT token is provide

login and logout methods are located in [AuthController](../main/src/main/java/fr/arnaud/cleanarchitecture/infrastructure/delivery/controller/auth/AuthController.java)  
after a regular login a valid token is returned
~~~~
{
    "currentToken": "eyJh...hTJg",
    "refreshToken": "eyJ...MsR0",
    "expiresIn": 36000,
    "refreshExpiresIn": 1800
}
~~~~

you have to call each web services with a bearer token in headers

~~~~
curl --location --request GET 'http://localhost:8090/v1/teams/123e4567-e89b-12d3-a456-426614174000' --header 'Authorization: Bearer eyJh...ERBu_KzA'
~~~~

### Code coverage

Tests are coverage via jacoco and eclEmma sts plugin.  

![image](https://user-images.githubusercontent.com/7325301/200142500-6c78859f-8812-4d23-abba-0d4397175180.png)


## coming soon

### Domain Driven Design

core services need to be more "DDD compliant" :-)  
need non anemic entities object and more business oriented service  

### resilience

add Rate Limit on controller
add exponential back off on consumer

use resillience4j

### Grafana & prometheus

https://piotrminkowski.com/2022/11/03/spring-boot-3-observability-with-grafana/

### SAGA or LRA support 

add SEATA or AXION IQ support ?

### Minikube

add minikube deployment files

https://wordpress.com/support/markdown-quick-reference/
