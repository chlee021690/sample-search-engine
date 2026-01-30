# Sample Spring Boot with ElasticSearch

A simple Spring Boot project with search engine capabilities using MongoDB, ElasticSearch, and Redis.

## Tech Stack

- **API**: Spring Boot 2.7.18
- **Database**: MongoDB (stores raw text and documents)
- **Search Engine**: ElasticSearch (indexes and searches documents)
- **Cache**: Redis (caches frequently accessed data)
- **Hosting**: Heroku-ready configuration

## Features

- RESTful API for document management
- Full-text search powered by ElasticSearch
- MongoDB for persistent storage
- Redis caching for improved performance
- CRUD operations on documents
- Search documents by content, title, or author

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- MongoDB (local or cloud instance)
- ElasticSearch 7.x
- Redis (local or cloud instance)

## Local Setup

### 1. Clone the repository

```bash
git clone https://github.com/chlee021690/sample-spring-boot-with-elasticsearch.git
cd sample-spring-boot-with-elasticsearch
```

### 2. Configure application properties

Update `src/main/resources/application.properties` with your local configurations:

```properties
# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/searchengine

# ElasticSearch
spring.elasticsearch.uris=http://localhost:9200

# Redis
spring.redis.host=localhost
spring.redis.port=6379
```

### 3. Start required services

**MongoDB:**
```bash
# Using Docker
docker run -d -p 27017:27017 --name mongodb mongo:latest
```

**ElasticSearch:**
```bash
# Using Docker
docker run -d -p 9200:9200 -e "discovery.type=single-node" --name elasticsearch elasticsearch:7.17.9
```

**Redis:**
```bash
# Using Docker
docker run -d -p 6379:6379 --name redis redis:latest
```

### 4. Build and run the application

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Create a Document
```bash
POST /api/documents
Content-Type: application/json

{
  "title": "Sample Document",
  "content": "This is the content of the document with searchable text",
  "author": "John Doe"
}
```

### Get All Documents
```bash
GET /api/documents
```

### Get Document by ID
```bash
GET /api/documents/{id}
```

### Update a Document
```bash
PUT /api/documents/{id}
Content-Type: application/json

{
  "title": "Updated Document",
  "content": "Updated content",
  "author": "Jane Doe"
}
```

### Delete a Document
```bash
DELETE /api/documents/{id}
```

### Search Documents
```bash
GET /api/documents/search?query=searchable
```

### Get Documents by Author
```bash
GET /api/documents/author/John%20Doe
```

## Heroku Deployment

### 1. Create a Heroku app

```bash
heroku create your-app-name
```

### 2. Add required add-ons

```bash
# MongoDB Atlas (recommended)
heroku addons:create mongodbatlas:sandbox

# ElasticSearch (Bonsai or SearchBox)
heroku addons:create bonsai:sandbox
# OR
heroku addons:create searchbox:minimal

# Redis
heroku addons:create heroku-redis:hobby-dev
```

### 3. Set environment variables

```bash
heroku config:set MONGODB_URI=<your-mongodb-uri>
heroku config:set ELASTICSEARCH_URI=<your-elasticsearch-uri>
heroku config:set REDIS_HOST=<your-redis-host>
heroku config:set REDIS_PORT=<your-redis-port>
heroku config:set REDIS_PASSWORD=<your-redis-password>
```

### 4. Deploy to Heroku

```bash
git push heroku main
```

### 5. Open your application

```bash
heroku open
```

## Testing the Application

### Using cURL

**Create a document:**
```bash
curl -X POST http://localhost:8080/api/documents \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Machine Learning Basics",
    "content": "Machine learning is a subset of artificial intelligence that enables systems to learn from data",
    "author": "AI Expert"
  }'
```

**Search for documents:**
```bash
curl "http://localhost:8080/api/documents/search?query=machine+learning"
```

### Using Postman

Import the API endpoints into Postman and test all CRUD operations and search functionality.

## Architecture

```
┌─────────────────┐
│   Client/API    │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│  Spring Boot    │
│   Application   │
└────────┬────────┘
         │
    ┌────┼────┐
    │    │    │
    ▼    ▼    ▼
┌───────┐ ┌──────────────┐ ┌───────┐
│MongoDB│ │ElasticSearch │ │ Redis │
│(Store)│ │   (Search)   │ │(Cache)│
└───────┘ └──────────────┘ └───────┘
```

### Data Flow

1. **Create/Update**: Documents are saved to MongoDB and indexed in ElasticSearch
2. **Read**: Data is cached in Redis for faster retrieval
3. **Search**: ElasticSearch provides fast full-text search capabilities
4. **Delete**: Documents are removed from all three systems

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/searchengine/
│   │       ├── SearchEngineApplication.java
│   │       ├── config/
│   │       │   └── RedisConfig.java
│   │       ├── controller/
│   │       │   └── DocumentController.java
│   │       ├── model/
│   │       │   ├── DocumentEntity.java
│   │       │   └── SearchableDocument.java
│   │       ├── repository/
│   │       │   ├── mongo/
│   │       │   │   └── DocumentRepository.java
│   │       │   └── elasticsearch/
│   │       │       └── SearchableDocumentRepository.java
│   │       └── service/
│   │           └── DocumentService.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
```

## Technologies Used

- **Spring Boot**: Framework for building the REST API
- **Spring Data MongoDB**: MongoDB integration
- **Spring Data Elasticsearch**: ElasticSearch integration
- **Spring Data Redis**: Redis caching
- **Lombok**: Reduce boilerplate code
- **Maven**: Build and dependency management

## License

This project is open source and available under the MIT License.