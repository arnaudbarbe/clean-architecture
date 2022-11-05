# clean-architecture

Personal implementation of a clean architecture

## folders organization
* client (
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

You need to start containers in src/main/resources/dockercompose/docker-compose.yml
before running test or start the spring boot server

## Features added
### event sourcing

events are sent on create/update/delete operations.
We used Aspect annotation in infrastructure/configuration/eventdriven/aspect

see below an example for League object. Whenever the fr.arnaud.cleanarchitecture.core.service.league.DomainLeagueService.createLeague() method is called, eventAfterCreateLeague() is triggered and a message with the dto is sent.
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
Publisher are located in fr/arnaud/cleanarchitecture/infrastructure/configuration/rabbitmq/publisher/v1
~~~~
package fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.publisher.v1;
...
@MessagingGateway
public interface LeagueEventPublisher {

    @Gateway(requestChannel = "createLeagueEventV1OutboundChannel")
    void createLeagueEvent(Event<LeagueDto> event);
    
    @Gateway(requestChannel = "updateLeagueEventV1OutboundChannel")
    void updateLeagueEvent(Event<LeagueDto> event);

    @Gateway(requestChannel = "deleteLeagueEventV1OutboundChannel")
    void deleteLeagueEvent(Event<UUID> event);
}
~~~~

### persistence

I'm using postgres, mongodb and cassandra to persist data.
This is just for fun, don't try this at home.

### Swagger

Swagger is available under
http://localhost:8090/swagger-ui/index.html

### HATEOAS

HATEOAS return link to delete, update, get and getAll on getOne and getAll operations

Each controller return XXXModel object whereas XXXDto are used as input object.
XXXModel extends RepresentationModel<ChampionshipModel> that provided HATEOAS features.
 
XXXModel are enrich in Controller
~~~~
public ResponseEntity<List<ChampionshipModel>> getChampionships() {
		List<ChampionshipModel> models = this.championshipService.getChampionships().stream()
        		.map(ChampionshipModel::fromEntity)
        		.map(model -> model.add(getSelfLink(model.getId())))
        		.map(model -> model.add(getCreateLink()))
        		.map(model -> model.add(getUpdateLink(model.getId())))
        		.map(model -> model.add(getDeleteLink(model.getId())))
        		.map(model -> model.add(getGetAllLink()))
        		.toList();

		return ResponseEntity
	    	      .status(HttpStatus.OK)
	    	      .body(models);
}
~~~~

JSON return on a getLeague method with id 515c419a-f59e-4814-a05a-cab9c09a20b9.
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

Keycloack is used in a different way that we could see in example.
I don't want to use keycloak login screen.

login and logout methods are located in fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.auth.AuthController
after a regular login that return a valid token

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

![image](https://user-images.githubusercontent.com/7325301/200140713-5ebf64b2-4fb0-426c-a57a-2076c41c8239.png)

## coming soon


### resilience

add Circuit Breaker, Bulk Head, Cache on gateway
add Rate Limit on controller
add exponential back off on consumer

use resillience4j

### Grafana & prometheus

https://piotrminkowski.com/2022/11/03/spring-boot-3-observability-with-grafana/

### SAGA or LRA support 

add SEATA support ?

### Minikube

add minikube deployment files

https://wordpress.com/support/markdown-quick-reference/
