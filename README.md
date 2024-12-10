# Personal Blog RESTful API

A RESTful API for managing blog articles with CRUD operations.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
  
## Installation

To set up the project locally, follow these steps:

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/personal-blog-api.git
   ```

2. Ensure you have [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) and [Maven](https://maven.apache.org/download.cgi) installed.

3. Set up your PostgreSQL database:
    - Create a database named `blogdb`.
    - Update the `src/main/resources/application.properties` file with your database credentials.

4. Build the project:
   ```
   ./mvnw clean install
   ```

5. Run the application:
   ```
   ./mvnw spring-boot:run
   ```

## Usage

Once the application is running, you can interact with the API using tools like Postman or curl.

### API Endpoints

- **Create Article**
    - `POST /api/articles`
    - Request Body:
      ```json
      {
        "title": "Your Article Title",
        "content": "Your article content",
        "tags": "tag1, tag2"
      }
      ```

- **Get All Articles**
    - `GET /api/articles`

- **Get Article by ID**
    - `GET /api/articles/{id}`

- **Update Article**
    - `PUT /api/articles/{id}`
    - Request Body:
      ```json
      {
        "title": "Updated Title",
        "content": "Updated content",
        "tags": "tag1, tag2"
      }
      ```

- **Delete Article**
    - `DELETE /api/articles/{id}`

- **Get Articles by Tag**
    - `GET /api/articles/filter?tag=yourTag`

- **Get Paginated Articles**
    - `GET /api/articles/paginated?page=0&size=10`

- **Get Sorted Articles**
    - `GET /api/articles/sorted?page=0&size=10&sortBy=publishedAt&sortDirection=asc`
