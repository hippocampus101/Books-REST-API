
# Book Management REST API

## Description
This Book Management REST API allows users to manage books and authors, providing full CRUD operations for both entities. Built using Spring Boot and PostgreSQL, this API ensures a clear separation of concerns with a 3-layer architecture: Controller, Service, and Persistence.


## Usage
You can interact with the API using tools like Postman or curl. Below are examples of how to use the API.

### Example Request
To create a new author:
```http
POST /authors
Content-Type: application/json

{
  "name": "Author Name",
  "age": 45
}
```

## Endpoints

### Authors
#### Create an Author
- **URL:** `/authors`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "name": "string",
    "age": "integer"
  }
  ```
- **Response:** `201 Created` with author details.

#### List All Authors
- **URL:** `/authors`
- **Method:** `GET`
- **Response:** `200 OK` with an array of authors.

#### Find an Author by ID
- **URL:** `/authors/{id}`
- **Method:** `GET`
- **Response:** 
  - `200 OK` with author details if found.
  - `404 Not Found` if the author does not exist.

#### Full Update an Author
- **URL:** `/authors/{id}`
- **Method:** `PUT`
- **Request Body:**
  ```json
  {
    "id": "integer",
    "name": "string",
    "age": "integer"
  }
  ```
- **Response:** `200 OK` with updated author details or `404 Not Found`.

#### Partial Update an Author
- **URL:** `/authors/{id}`
- **Method:** `PATCH`
- **Request Body:**
  ```json
  {
    "name": "string",  // Optional fields to update
    "age": "integer"   // Optional fields to update
  }
  ```
- **Response:** `200 OK` with updated author details or `404 Not Found`.

#### Delete an Author
- **URL:** `/authors/{id}`
- **Method:** `DELETE`
- **Response:** `204 No Content`.

#### Delete All Authors
- **URL:** `/authors`
- **Method:** `DELETE`
- **Response:** `204 No Content`.

### Books
(You can add similar endpoints for the `BookController` here.)

## Testing
To run unit and integration tests, execute the following command:
```bash
mvn test
```
This project uses Mockito and MockMvc for testing.

## Technologies Used
- Java
- Spring Boot
- PostgreSQL
- Maven
- Mockito
- MockMvc
