# JPA Doodle

## Prerequisites

- [Maven](https://maven.apache.org/)
- [Node](https://nodejs.org/en/)
- [Mysql](https://www.mysql.com/fr/)

## How to use

This section provides a quick start guide on to use this application.

### Backend

We are using [Spring Boot](https://spring.io/projects/spring-boot).

1. `cd` into the `backend` folder.
2. Start by creating a database using mysql with the name `doodle`.
3. Install all the dependencies using `mvn clean install`.
4. Start the server with the command `mvn spring-boot:run`.

### Frontend

We are using [Vue.js](https://vuejs.org/).

1. `cd` into the `frontend` folder.
2. Install all the dependencies using `npm install`.
3. Start the front development server using `npm run serve`.

## Goal

Our goal here is to create a doodle project. 

A user can : 
1. Go to our main page
2. Create a poll
3. Add some choices
4. Share his poll with his friend by sending them the link
5. Vote for a choice
