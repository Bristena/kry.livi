# Summary

As a part of scaling the number of services running within a modern health
tech company we need a way to make sure our systems are running
smoothly. None of the monitoring tools that we have looked at satisfy our
requirements so we have decided that we will build one ourselves. What we
want you to do is to build a simple service poller that keeps a list of
services (defined by a URL), and periodically performs a HTTP GET request
to each and stores a record of the response ("OK" or "FAIL"). Apart from the
polling logic we want to have all the services visualised and easily managed
in a basic UI presenting all the services together with their status

The endpoint can be used via swagger

## Prerequisites
1. Java 15
2. Gradle 7.1
3. Docker

## Running the application
1. Use the command `docker-compose up` from src/main/resources to start the MySql database 
2. Run `./gradlew bootRun` in a terminal to start the application
