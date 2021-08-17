# Tax Calculator

This application is a REST API for calculating taxes from a shopping basket. It was built using a Clean Architecture (Hexagonal) using clean code principles. 

The project has the following set of features:
- Given a order basket, print the receipt details with the name of all items and their price (with taxes) rounded up to the nearest 0.05, along with the extra taxes and total 

The idea of this project is to be a standalone package which can be deployed anywhere (e.g., a microservice architecture). For simplicity, it uses a H2 in-memory database (but could be replaced by any other rdbms) and runs under a embedded tomcat (with Spring).

It uses [Docker](https://docs.docker.com/get-docker/) to build, test, and run the application.


## Build

- Run the following command under the root folder:
  ```
  $ docker build -t tax-calculator .
  ```

- The command will build an image and while doing so, execute both unit and integration tests. 

## Run

With the built image, one can execute the project running:
  ```
  docker run --publish 8080:8080 tax-calculator
  ```

Once it is running, the API accepts **POST** requests in the schema described on the local [OpenAPI spec](http://localhost:8080/swagger-ui.html) (make sure the created container from the command above is running).

For example:
```
[
    {
        "name":"imported bottle of perfume",
        "quantity":1,
        "value":27.99
    },
    {
        "name":"bottle of perfume",
        "quantity":1,
        "value":18.99
    },
    {
        "name":"packet of headache pills",
        "quantity":1,
        "value":9.75
    },
    {
        "name":"imported box of chocolates",
        "quantity":1,
        "value":11.25
    }
]
```


# Disclaimer

This project was built around the Spring framework, using H2 in-memory database with Hibernate ORM. For testing, the project includes both Unit and Integration tests, built using JUnit and Mockito, along with AssertJ.