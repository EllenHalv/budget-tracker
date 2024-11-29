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

## Frontend
- **Link to frontend**: [Budget Tracker Frontend](https://github.com/EllenHalv/budget-tracker-frontend)

## TODO (In Progress)
- UI view for displaying expenses.