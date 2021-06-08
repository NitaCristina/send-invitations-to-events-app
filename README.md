### SchoolForJava - final project ###

## Description ## 
Sending invitations to events management app

## Functionalities ##
- Create users with the ROLE_USER by default. It is already created a single user with ROLE_ADMI
- CRUD operations on users - some of them allowed only for users with ROLE_ADMIN, for ROLE_USER, or allowed for both user types
- CRUD operations on events - some of them allowed only for users with ROLE_ADMIN, for ROLE_USER, or allowed for both user types
- Receive a list with created events for logged in user
- Receive a list with all invitations (received, accepted, rejected) for logged in user
- Receive a list with received invitations for logged in user
- Receive a list with accepted invitations for logged in user
- Receive a list with rejected invitations for logged in user
- Logged in user can accept or reject his received invitations, any other answers are invalid
- Receive a list with invitations that have been sent to logged in user's created events
- Logged in user can acces only his recources (for example: edit an event created by him and not by other users)
- Logged in user cand sent invitations to a list of users, to a scpecific event

## Technologies ##
Java 11
Maven
Spring Boot with Spring Web (REST Services), Spring Data, Spring AOP, Spring Security
PostgreSQL
Junit 5
Mockito
Postman

## Run locally ##
Run the application from IDE, by default it will run on port 8080 with Tomcat

## Database cofiguration ##
The url, username and password are set in application.properties
