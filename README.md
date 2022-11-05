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
### event driven

events are sent on create/update/delete operations.
We used Aspect annotation in infrastructure/configuration/eventdriven/aspect

### persistence

I'm using postgres, mongodb and cassandra to persist data.
This is just for fun, don't try this at home.

### Swagger

Swagger is available under
http://localhost:8090/swagger-ui/index.html

### HATEOAS

HATEOAS return link to delete, update, get and getAll on getOne and getAll operations

### security 

An instance of Keycloak was added in docker compose file

admin interface is available at http://localhost:8080/

* user : admin
* password : admin

2 users exist
- user: user1, password: user1, role: USER
- user: admin1, password: admin1, role: ADMIN

## coming soon
### Code coverage

add a test code coverage report
https://openclover.org/index

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
