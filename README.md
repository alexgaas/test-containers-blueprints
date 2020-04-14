[![license](https://img.shields.io/github/license/mashape/apistatus.svg?maxAge=2592000)](https://github.com/alexgaas/test-containers-blueprints/master/LICENSE)

# test-containers-blueprints

Thats full example of [test-containers](https://www.testcontainers.org/) usage.

`Testcontainers is a Java library that supports JUnit tests, providing lightweight, throwaway instances of common databases, Selenium web browsers, or anything else that can run in a Docker container.`

Example includes:

- Basic Spring Boot microservices setup
- Simple onion archetecture (controller/service/repository/model) and DDD support by model
- Basic Webflux support
- Basic `flyway` support for example db script migration
- Unit and integration tests with flayway and MySQL testcontainers container 
- Docker compose file to start Spring Boot Application with instance of MySQL

## Build / Deploy

- start MySQL simply using `docker-compose` file (sure [Docker for Mac tools](https://docs.docker.com/docker-for-mac/install/) must be installed before) as `docker-compose up`
If you getting any trouble you can use `docker-compose down -v` to clean up previews docker images
- start `test-containers-blueprints` with **gradle**

## testcontainers integration tests
Integration tests placed in [test](https://github.com/alexgaas/test-containers-blueprints/tree/master/src/test/java/integration). 

### License

Licensed under the [MIT License](https://github.com/alexgaas/test-containers-blueprints
/blob/master/README.md).
