# ToDoApp

#### Description:

#### Login Page:
In Login page(http://HOST:PORT/login) you can login with
 - USERNAME:admin
 - PASSWORD:admin



In home page (http://HOST:PORT/dashboard) you will find four menu 

1. Dashboard

- You will able to see count of todo by STATUS

2. Add TODO
  - By clicking Add Todo (http://HOST:PORT/todo-items/add)  you will able to add new todo .

3. View Todo Items

- a.By clicking View Todo Items(http://HOST:PORT/todo-items/view-all)  you will able to see all todo list .
- b. rightmost row there is a "Info" button to view details and change log.
- c.you can Edit todo by clicking edit button at rightmost row on the table.
- d.you can Dlete todo by clicking delete button at rightmost row on the table.
- e.one can search todo on the search bar by date and Status

4. Logout

- a.By clicking Logout you will logged out and redirected to login page  .





## Backend

#### Technology used: 

- spring-boot
- spring-boot-starter-web
- spring-data-jpa
- spring-boot-starter-security
- MySQL
- Java 8
- maven



## Frontend

#### Technology used: 

- spring-boot-starter-thymeleaf
- jquery-vuex
- ajax



#### Database configuration: 

*  You can find database configuration in  **application.properties** file
It looks like below:
 - spring.datasource.url = jdbc:mysql://localhost:3306/"YOUR_DB_NAME"?createDatabaseIfNotExist=true&serverTimezone=UTC
 - spring.datasource.username ="YOUR_DB_USER_NAME"
 - spring.datasource.password ="YOUR_DB_PASSWORD"

* When run the project It will create a amdin user for the first Time .
with below creadintial:
-USER NAME:admin
-PASSWORD :admin

you can also find a database in sql folder . which named "tododb.sql"
```
ToDoApp
├── sql
├── src
├── target
```


#### Installation guide:

* Build Spring Boot Project with Maven:
To be able to run your Spring Boot app you will need to first build it. To build and package a Spring Boot app into a single executable Jar file with a Maven, use the below command. You will need to run it from the project folder which contains the pom.xml file.
 - "mvn install"

OR,You can also use Maven plugin to run your Spring Boot app. Use the below example to run your Spring Boot app with Maven plugin:
 - "mvn spring-boot:run"



