# clean-architecture

Personal implementation of a clean architecture

folders are organized like this :

 * core (business part)
    * exception 
    * model (Entities)
    * repository (interfaces to storage, should be implemented by infrastructure)
    * service (Business rules)
    * use case (Business rules too but oriented use case)
 * infrastructure (technical part)
    * configuration (configuration for each framework/persistence/etc...)
    * delivery (from other system)
        * consumer (MOM consumers)
        * controller (REST controller)
        * task (cron task to execute async process)
        * gateway (to other system)
        * persistence (repositories for each storage system)
