# Budget Tracker Application

## Overview
The **Budget Tracker Application** enables users to manage their finances by setting a budget, logging expenses, and monitoring their spending. Designed with both functionality and security in mind, this application is built with a Java backend and a React-based frontend, using Oauth2-based authentication.

## Features
- **Budget Management**: Set budgets, track expenses, and monitor remaining balances.
- **Database Integration**:
    - **H2 Database**: Used for local testing.
    - **MySQL Database**: Used in the development environment.
- **Security**:
    - OAuth2-based authentication for secure user access. 🔑
    - Endpoint protection in controllers.
    - Admin user role with additional CRUD privileges for advanced management.
- **API Documentation**: API documentation available via Swagger UI.
    - Access the documentation after starting the application at:  
      `http://localhost:8080/swagger-ui.html`.

## Getting Started  

### Prerequisites  
Before starting, ensure you have the following installed:  
- **Java 17+**: [Download Java](https://www.oracle.com/java/technologies/javase-downloads.html)  
- **Node.js**: [Download Node.js](https://nodejs.org/)  
- **MySQL**: [Download MySQL](https://dev.mysql.com/downloads/)  
- **Maven**: [Download Maven](https://maven.apache.org/download.cgi)  

### Backend Setup  
1. Clone the backend repository:  
   ```bash
   git clone https://github.com/EllenHalv/budget-tracker.git
   cd budget-tracker
   ```
2. Configure the MySQL database:  
   - Create a database in MySQL (e.g., `budget_tracker`).  
   - Update the `application.properties` file with your MySQL credentials:  
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/budget_tracker
     spring.datasource.username=<your-username>
     spring.datasource.password=<your-password>
     spring.jpa.hibernate.ddl-auto=update
     ```  

3. Build and run the backend:  
   ```bash
   mvn clean install
   java -jar target/budget-tracker-0.0.1-SNAPSHOT.jar
   ```  
4. Verify the backend is running:  
   Open your browser and visit `http://localhost:8080/swagger-ui.html` for API documentation.  

### Frontend Setup  
For instructions on setting up the React frontend, visit the repository:  
[Budget Tracker Frontend Repository](https://github.com/EllenHalv/budget-tracker-frontend)  

## Learn More  
### Backend  
- **Java**: Learn how to build robust backend systems using Java.  
  - [Oracle Java Documentation](https://docs.oracle.com/javase/8/docs/)  
  - [Spring Boot Documentation](https://spring.io/projects/spring-boot)  
- **Databases**: Get started with relational databases.  
  - [H2 Database Documentation](http://www.h2database.com/html/main.html)  
  - [MySQL Documentation](https://dev.mysql.com/doc/)  
- **OAuth2**: Understand secure authentication and authorization.  
  - [OAuth2 Guide](https://oauth.net/2/)  
  - [Spring Security and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)  
- **Swagger UI**: Explore API documentation tools.  
  - [Swagger Documentation](https://swagger.io/tools/swagger-ui/)  

## TODO (In Progress)
- UI view for displaying expenses.
