# clean-architecture

Personal implementation of a clean architecture

##folders organization

 * core (business part)
    * exception 
    * model (Entities)
    * repository (interfaces to storage, should be implemented by infrastructure)
    * service (Business rules)
    * use case (Business rules too but use case oriented)
 * infrastructure (technical part)
    * configuration (configuration for each framework/persistence/etc...)
    * delivery (from other system)
        * consumer (MOM consumers)
        * controller (REST controller)
        * task (cron task to execute async process)
    * gateway (to other system)
    * persistence (repositories for each storage system)

##persistence

I'm using postgres, mongodb and cassandra to persist data.
This is just for fun, don't do this at home.

## Swagger

Swagger is available under
http://localhost:8080/swagger-ui/index.html

## async IO
In next release I want to add async IO support to produce event on CRUD operations
https://www.asyncapi.com/

## security 
To be complete these web services need to be secure
https://www.baeldung.com/security-spring

## SAGA or LRA support 
add SEATA support ?

## Running tests

You need to start containers in src/main/resources/dockercompose/docker-compose.yml
before running test or start the spring boot server

https://wordpress.com/support/markdown-quick-reference/
