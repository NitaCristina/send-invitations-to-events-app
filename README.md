### SchoolForJava - final project ###

## Description ## 
Sending invitations to events management app

## Functionalities ##
- Create users with the ROLE_USER by default. It is already created a single user with ROLE_ADMIN
- CRUD operations on users - some of them allowed only for users with ROLE_ADMIN, only for users with ROLE_USER, or allowed for both types of users
- CRUD operations on events - some of them allowed only for users with ROLE_ADMIN, only for users with ROLE_USER, or allowed for both types of users
- Only user with ROLE_ADMIN can visualize all invitations that have been sent, or a specific invitation
- Receive a list with created events for logged in user
- Receive a list with all invitations (received, accepted, rejected) logged in user has
- Receive a list with received invitations logged in user has
- Receive a list with accepted invitations logged in user has
- Receive a list with rejected invitations logged in user has
- Logged in user can accept or reject his received invitations, any other answers are invalid
- Logged in user can receive a list with invitations that have been sent to users in order to participate to his event/events
- Logged in user can acces only his resources (for example: edit an event created by him and not by other user)
- Logged in user can sent invitations to a list of users, to a specific event

## Technologies ##
- Java 11
- Maven
- Spring Boot with Spring Web (REST Services), Spring Data, Spring AOP, Spring Security
- PostgreSQL
- Junit 5
- Mockito
- Postman

## Run locally ##
Run the application from IDE, by default it will run on port 8080 with Tomcat

## Database cofiguration ##
The url, username and password are set in application.properties
