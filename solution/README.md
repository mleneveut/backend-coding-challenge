README
====

Initialisation
----
The application uses a MySQL database, located at localhost:3306 with user root/admin.

You should have a running MySQL with a 'codingchallenge' database :

```
$ mysql -u root -p admin
$ CREATE database codingchallenge;
$ exit
```

Running the application
----
To run the application :
  * gulp dev (if any modifications where made to front code)
  * cd solution
  * mvn clean install
  * java -jar target/challenge-1.0.0.jar
  * open your browser at http://localhost:8080
  
The gulp target has been modified so that all static content is copied in solution/src/main/resources/static to be served by Spring.

Specifications
----
This application runs :
  * Spring Boot 1.5.9 (Spring core 4.3.13)
  * Spring web with undertow instead of Tomcat (lighter, faster, well fit for our needs)
  * Spring actuator
  * Spring Data JPA over Hibernate
  * HikariCP for the database connection pool
  * Flyway to handle the database schema migrations
  * A MySQL database
  * Jackson to handle JSON
  * Lombok to write few less code
  * MapStruct to map DTO to DAO and reverse
  * Swagger for the API documentation
  
Architecture details
----
As a Spring Boot application, we have the following architecture :
  * web : contains the Controllers, the endpoints of our API
  * service : contains the business service, handling transactions
  * repository : contains the Spring Data JPA interface to query the database
  * entity : the database model
  * dto : the representation view of what is exposed to the front-end
  * exceptions : the exceptions thrown by the application
  * mapper : the MapStruct mappers

The web/RestExceptionHandler handles the REST exception, to give some feedback to the front-end in case of error.

Modifications to the front-end
----
Few modifications have been made to the front-end, in order to catch the errors from our API. For example when we add an expense with a wrong date or amount.

Run the tests
----
To run the tests you will need a specific database : 'codingchallengetests'

```
$ mysql -u root -p admin
$ CREATE database codingchallengetests;
$ exit
```

Then you can run the tests :
  * mvn clean test
  
Use Case 3
---
To be able to eventually put some cache and error management in place (in case the public API is not reachable for example), it has been decided to host the conversion rate service in the back-end.

We put a EhCache for the EUR - GBP conversion rate, living for one hour. The public API will be called only once per hour. We could also store the last value retrieved and send it to the front-end in case of error calling the public API.

The converted amount on client-side will then be sent to the back-end and stored in database. The original Euro amount will not be stored. This could be easily changed if the Use Case needs it.

Use Case 4
----
The VAT is calculated on client-side when the user enters an amount. It uses a config 'vat' attribute.

This display is only used for user's information, it will not be stored in the database, and will be calculated again on back-end when retrieving data from DB. We could also remove the back-end calculation and display the VAT in the result list from the same client-side calculation. It's up to the PO. 

This could be easily changed if the Use Case needs to.
