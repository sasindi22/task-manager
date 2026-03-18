# task-manager

A modern, full-stack Task Management application featuring a **Spring Boot** REST API and an **Angular** reactive frontend. This project demonstrates clean architecture, responsive design, and robust CRUD functionality.

## tech stack
* **frontend:** angular 17+ (standalone components, reactive forms)
* **backend:** spring boot 3.x (java 17, spring data jpa)
* **database:** mysql
* **api testing:** postman verified 

## core features
* **comprehensive crud:** create, view, update, and delete tasks seamlessly.
* **advanced search:** search by keywords (case-insensitive) and filter by status (`to_do`, `in_progress`, `done`).
* **reactive forms:** implemented preferred angular reactive forms for scalable state management and validation.
* **real-time validation:** mandatory title field with instant feedback.
* **error handling:** centralized backend exception handling and frontend api error logging.
* **cors enabled:** fully configured for secure frontend-backend communication via `@CrossOrigin`.

## database setup
1.  **create database:** open your mysql terminal/workbench and run:
    ```sql
    CREATE DATABASE tasker_db;
    ```
2.  **configure credentials:** * navigate to `tasker-backend/src/main/resources/application.properties`.
    * update `spring.datasource.username` and `spring.datasource.password` with your local mysql credentials.
3.  **automatic schema:** the app uses `hibernate.ddl-auto=update`, so tables will be generated automatically on the first run.

## how to run
### backend (intellij/terminal)
1.  open the `tasker-backend` folder in your ide.
2.  ensure **java 17** is selected as the project sdk.
3.  run `TaskerBackendApplication.java`. The server starts on `http://localhost:8080`.

### frontend (vs code/terminal)
1.  open the `tasker-frontend` folder.
2.  run `npm install` to download dependencies.
3.  run `ng serve` and navigate to `http://localhost:4200`.
