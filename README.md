# clean-architecture

Personal implementation of a clean architecture

## folders organization

* core (business part)
  * exception 
  * model (Entities, Values object and aggregate)
  * repository (interfaces to storage, should be implemented by infrastructure)
  * service (Business rules)
  * use case (Business rules too but use case oriented)
* infrastructure (technical part)
  * client (client like codegen generated code)
  * configuration (configuration for each framework/persistence/etc...)
    * advice (Exception processor)
    * cassandra 
    * cors
    * eventdriven (aspect classes around services classes to throw messages on create/update/delete)
    * mongo
    * postgres
    * rabbitmq (event and service exchange/queue configurations)
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

## event driven

events are sent on create/update/delete operations.
We used Aspect annotation in infrastructure/configuration/eventdriven/aspect

## persistence

I'm using postgres, mongodb and cassandra to persist data.
This is just for fun, don't try this at home.

## Swagger

Swagger is available under
http://localhost:8080/swagger-ui/index.html

## HATEOAS

HATEOAS return link to delete, update, get and getAll on getOne and getAll operations

## resilience

add Circuit Breaker, Bulk Head, Cache on gateway
add Rate Limit on controller
add exponential back off on consumer

use resillience4j

## security 

An instance of Keycloak was added
admin interface is available at http://localhost:8080/
user : admin
password : admin

2 users exist
- user: user1, password: user1
- user: admin1, password: admin1

## SAGA or LRA support 

add SEATA support ?

## Running tests

You need to start containers in src/main/resources/dockercompose/docker-compose.yml
before running test or start the spring boot server

https://wordpress.com/support/markdown-quick-reference/
