# Qaulibrate API 2.0

This is a Demo Microservice REST APIs written in Java 8 using Spring Boot.
It tries to follow API maturity model Level 3 using Swagger and asciidoc for API documentation.
Integration test using JUnit and H2DB for "test" profile. (No Mocks !)

CI/CD : Using Travis for CI/CD : (Currently it runs build only (no deployment) on every commit and merge.)

## Minimum Prerequisites

* Java 8 SDK
* MySQL 5.7.21 running on port 3306 with an existing DB schema as "qualibrate"

## Build

Build process includes,
* Test Cases
* StyleCheck (in process)
* Code Quality Check (in process)
* asciidoc doc job to generate API documentation
* build executable jar of spring-boot app




You can build and Run this application in two ways as below


### 1. Build in local environment
	- clone this repository
	- make sure you have JDK 8 installed
	- in root of project run below command
	
```
> ./gradlew build

```
Note : For Windows environment : sometimes asciidoc fails the build (due to a bug with Ruby). in that case you can use build process using docker as below.
 

### 2. Build using docker container
 I have created a simple docker build machine based on official Java 8 image which can be used for any gradle project. You need to provide Git URL of the code and shared volume path by passing environment variable to docker. The container will clone the repository and will also build the project.
 
Make sure your docker environment is already setup and you can mount VOLUME to docker containers

You need to run a docker image and pass two parameters as below

1. Git URL : in this case its same as this repository's URL
2. Docker's path : for mounted volume. so make sure you mount a volume and specify the path

Command to build using docker build machine

```
docker run --name {name-for-your-container} -e GIT_REPO_URL='https://github.com/krunalsabnis/qualibrate-java-api.git' -e DOCKER_PATH='{path-on-container-filesystem where git will clone and gradle build is run}' -i -v {your-local-dir}:{path-on-container-filesystem} krunalsabnis/spring-java-build:latest
```
Git repository will be cloned at {your-local-dir}.

You should be able to see build progress on console. Once done the build docker container remains running unless you stop it and can be reused
[more details ->  https://hub.docker.com/r/krunalsabnis/spring-java-build/]



## Run

This app can be run in two ways.

###1. Run in local setup
You need a local instance of MySQL running on port 3306 and should have database created with name as "qualibrate"

```
>  java -jar .\build\libs\qualibrate-java-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=local --spring.datasource.username={your db username} --spring.datasource.password={your db password}

```

###2. Docker Compose for [MySQL, Spring Boot App, nginx]

 - MySQL container
 - Spring Boot container
 - nginx container - as of now it hosts API documentation generated by asciidoc

 Once you have successfully build the project simply run below

```
> docker-compose up

```
## Once containers are up you can access APIs as below

## API Base URL 	:  http://localhost:8080
## Swagger Page	:  http://localhost:8080/swagger-ui.html
## API Documents	:  http://localhost:8085








