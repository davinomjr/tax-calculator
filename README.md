# Tax Calculator

This application is a REST API used for calculating taxes from a shopping basket. It was built using a Clean (Hexagonal) architecture and clean code principles. 

## Features
- Given a order basket, print the receipt details with the name of all items and their price (with taxes) rounded up to the nearest 0.05, along with the extra taxes and total
- Basic sales tax is applicable at a rate of 10% on all goods, except books, food, and medical products that are exempt
- Import sales tax is applicable on imported goods at a rate of 5%, with no exemptions
- The order must contain products that are *previously registered on the system* (as to identify whether the product is exempt/imported)
- The list of pre-registered products (case insensitive) on the system are as follows:
  
  1. Book
  2. Music CD
  3. Chocolate Bar
  4. Imported Box of Chocolates
  5. Imported Bottle of Perfume
  6. Bottle of Perfume
  7. Packet of Headache Pills
- The orders are persisted and can be accessed through H2 in-memory console on [http://localhost:8080/database](http://localhost:8080/database) using the credentials on application.properties [file](https://github.com/davinomjr/tax-calculator/blob/main/src/main/resources/application.properties).


## General

The idea of this project is to be a standalone package which can be deployed anywhere (e.g., a microservice architecture). 

For simplicity, it uses a H2 in-memory database (but could be replaced by any other RDBMS) and runs under a embedded Tomcat (with Spring).

It uses [Docker](https://docs.docker.com/get-docker/) to build, test, and run the application.


## How to Build

- Run the following command under the root folder (it can take a while on the first time due to dependencies being downloaded):
  ```
  $ docker build -t tax-calculator .
  ```

- The command will build an image and while doing so, execute both unit and integration tests. 

## How to Run

With the built image, one can execute the project running:
  ```
  docker run --publish 8080:8080 tax-calculator
  ```

Once it is running, the API accepts **POST** requests in the schema specified on the OpenAPI specification found on [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

For example, the following request:
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

Produces the following response from the API:
```
{
  "Basket" : [ {
    "Item" : {
      "Name" : "imported bottle of perfume",
      "Quantity" : 1,
      "Value" : "32.20"
    }
  }, {
    "Item" : {
      "Name" : "bottle of perfume",
      "Quantity" : 1,
      "Value" : "20.90"
    }
  }, {
    "Item" : {
      "Name" : "packet of headache pills",
      "Quantity" : 1,
      "Value" : "9.75"
    }
  }, {
    "Item" : {
      "Name" : "imported box of chocolates",
      "Quantity" : 1,
      "Value" : "11.85"
    }
  } ],
  "Sales Taxes" : "6.72",
  "Total" : "74.70"
}
```


You can use this [collection](TaxCalculator.postman.json) for testing the API using [Postman](https://www.postman.com/downloads/), which contemplates the examples included in the root folder.

# Disclaimer

This project was built around the Spring framework, using H2 in-memory database with Hibernate ORM. For testing, the project includes both Unit and Integration tests, built using JUnit and Mockito, along with AssertJ.

The API was designed to be a self-contained package (fat jar), which makes it deployable to multiple types of environments.

