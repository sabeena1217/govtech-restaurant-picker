##Tech Stack
This is developed using
- Java 17
- SpringBoot 3
- Thymeleaf
- H2 database
- OpenAPI for documentation

Moreover, I have used spring-sessions to facilitate the session management

By default, the web application is hosted in http://localhost:8080/

For API Swagger Definitions
http://localhost:8080/swagger-ui/index.html

##Logic 
- I have introduced a database script, to load the initial users and restaurants to the database. 
- You can do the same using the introduced REST API endpoints.
- When we select a user ticking the checkboxes, it is considered to as an invitation.
- Then a user may randomly pick any selection.
- We can revert the invitation/restaurant selection by un-ticking the checkboxes.
- Users, and their selected restaurant selections are maintained in the session and the database table both.

