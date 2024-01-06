## DNA Engineering Full-Stack Assignment
Build a CSV Parser.

## Table of content
- [Prerequisites](#prerequisites)
- [Before We begin](#before-we-begin)
- [Assignment](#assignment)
- [What we expect](#what-we-expect)
- [Bonus points](#bonus-points)

## Prerequisites
- Java 17
- Node Js v20.10.0

## Before we begin
- In this assignment, you will be asked to write, and test your code.
- Make sure you respect clean code guidelines.
- Read the assignment carefully.

## Description
You are invited to create a CSV parser using Java/Spring Boot, and build UI to display results using Next.js/React.

## Assignment

### Backend (CSV Parser)

#### Tasks

- Write a service in Java that will read and process the attached CSV(comma separated values) file at `data/employees.csv`.

- This service should read, extract and process data in a suitable data structure.

- Process this data to return the list of employees and a summary indicating the average salary for each job title.

### Frontend

#### Tasks
Implement a simple user interface that will allow the user to upload the file and display the results of your processing.

#### Interfaces

Respect the following design flow:

![Frontend interfaces](./static/interfaces.png)

- **Interface-1**: Contain an upload button.
- **Interface-2**: The Process button is added when you choose a file.
- **Interface-3**: 2 Tables showing the processing results.

**Table 1**: Employee information, displays a paginated list of employees.

**Table 2**: Jobs summary, displays for each job title, the average salary for employees.

## What we expect
- Write a concise, easy to understand code.
- Use good practices.
- Write unit tests for your java code.
- Append to this README your approach and provide instructions to run your project.

## Bonus points
- Implement your own CSV file parser instead of using a library.
- Use design patterns.





# Approach


## Backend
- **Service Layer**: Implemented business logic in the `EmployeeServiceImpl` which consumes the `CsvParserService` to process uploaded CSV files returns list of employees and then calculate average salaries by job title.
- **CSV Parsing**: `CsvParserServiceImpl` convert CSV data into `Employee` List and in case there's invalid CSV data it throws `CsvProcessingException` with a meaningful message.
- **Controller**: Exposed a REST API endpoint in `EmployeeController` to upload and process the CSV file and to return the processed data.
- **Exception Handling**: `CsvProcessingException`  is thrown in case, file is not csv, empty or contains invalid format.

## Tests

- **Unit Tests**: Wrote unit tests for the service layer and the web layer, using JUnit and Mockito. by first testing the controller layer by focusing more on request/response behavior and input validation and less on the business logic (mocked the service layer). and then tested the business logic within the service classes by putting more emphasis on the CSV parsing and average salary calculations.

## Frotend 
- **Custom Hook**: Used a custom `usePaginate` hook to paginate the employee data table, for modularity/reusability's sakes, and so that pagination logic can be used across components.
- **Avoiding unnecessary API calls By Persisting Results in Session Storage**:
Persisted the results in the browser's session storage until a new CSV file is uploaded, to one: prevent  data loss on page refresh and two : avoid unnecessary API calls for same CSV file.




# Instructions to run the project


## Backend
- Run `./mvnw spring-boot:run` in the root dir of the `backend` folder.
- To run tests, run `./mvnw test` in the root dir of the `backend` folder.

## Frontend
- Run `npm install` then `npm run dev` in the `frontend` folder.



