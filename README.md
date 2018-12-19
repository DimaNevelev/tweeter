# Consul POC Overview

This project is built from three services:
* Consul service for registry
* MongoDB
* Tweeter mini service

The MongoDB service is being registered to the Consul via REST API.  
The Tweeter service resolves a MongoDB instance using Consul DNS.

## Installation

To run it you will need the docker-compose tool.  
Run: 
```
docker-compose up
```
It will run three containers and a subnet.
* The consul service is created using the `consul:latest` image.
* The MongoDB is based on `mongo:4.1.6` and being registered to the consul using curl + REST API.
* The tweeter service is a Spring boot application which being built from this sources using maven wrapper when building the image.
* The default subnet is set to `173.17.1.0/24` and can be changed from the `.env` file.

## Usage

In order to see the consul UI, go to `http://locahost:8500`.  

You can add tweets by sendin post request to `http://locahost:8080/tweet`. For example, the following curl wil add a tweet:
```
curl -XPOST -H"Content-Type:application/json" -d'{"name":"me", "tweet":"my tweet"}' http://localhost:8080/tweet
```

You can see last tweets by sending a get request to `http://locahost:8080/feed` For example, the following curl will return you last tweets:
```
curl http://localhost:8080/feed
```

## Building the Tweeter server

You don't really need to build this, just run `docker-compose up`.  
If you still want to build the service, run:
```
./mvnw clean package
``` 
and then run:
```
java -jar target/tweeter.jar
```

By default, the build will be done using `dev` profile and will be built with an in-app embedded MongoDB.  
If the mongo resolver via DNS feature is required run 
```./mvnw clean package -Pprod``` 
The DNS URL can be changed in `src/main/java/resources/application.yml#spring.consul.mongodnsname`.  
For tests run:
```
./mvnw clean test
```