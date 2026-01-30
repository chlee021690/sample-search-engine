# API Examples

This document provides example API requests and responses for testing the Search Engine application.

## Prerequisites

Ensure the application is running:
```bash
mvn spring-boot:run
```

## Example Requests

### 1. Create a Document

**Request:**
```bash
curl -X POST http://localhost:8080/api/documents \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Introduction to Spring Boot",
    "content": "Spring Boot makes it easy to create stand-alone, production-grade Spring-based applications. It provides auto-configuration and embedded servers.",
    "author": "John Doe"
  }'
```

**Response (201 Created):**
```json
{
  "id": "63f8a1b2c4d5e6f7g8h9i0j1",
  "title": "Introduction to Spring Boot",
  "content": "Spring Boot makes it easy to create stand-alone, production-grade Spring-based applications. It provides auto-configuration and embedded servers.",
  "author": "John Doe",
  "createdAt": "2026-01-30T16:30:00",
  "updatedAt": "2026-01-30T16:30:00"
}
```

### 2. Create Another Document

**Request:**
```bash
curl -X POST http://localhost:8080/api/documents \
  -H "Content-Type: application/json" \
  -d '{
    "title": "ElasticSearch Basics",
    "content": "ElasticSearch is a distributed, RESTful search and analytics engine. It provides full-text search capabilities with powerful query DSL.",
    "author": "Jane Smith"
  }'
```

### 3. Get All Documents

**Request:**
```bash
curl http://localhost:8080/api/documents
```

**Response (200 OK):**
```json
[
  {
    "id": "63f8a1b2c4d5e6f7g8h9i0j1",
    "title": "Introduction to Spring Boot",
    "content": "Spring Boot makes it easy to create stand-alone, production-grade Spring-based applications...",
    "author": "John Doe",
    "createdAt": "2026-01-30T16:30:00",
    "updatedAt": "2026-01-30T16:30:00"
  },
  {
    "id": "63f8a1b2c4d5e6f7g8h9i0j2",
    "title": "ElasticSearch Basics",
    "content": "ElasticSearch is a distributed, RESTful search and analytics engine...",
    "author": "Jane Smith",
    "createdAt": "2026-01-30T16:31:00",
    "updatedAt": "2026-01-30T16:31:00"
  }
]
```

### 4. Get Document by ID

**Request:**
```bash
curl http://localhost:8080/api/documents/63f8a1b2c4d5e6f7g8h9i0j1
```

**Response (200 OK):**
```json
{
  "id": "63f8a1b2c4d5e6f7g8h9i0j1",
  "title": "Introduction to Spring Boot",
  "content": "Spring Boot makes it easy to create stand-alone, production-grade Spring-based applications...",
  "author": "John Doe",
  "createdAt": "2026-01-30T16:30:00",
  "updatedAt": "2026-01-30T16:30:00"
}
```

### 5. Search Documents

**Request:**
```bash
curl "http://localhost:8080/api/documents/search?query=Spring"
```

**Response (200 OK):**
```json
[
  {
    "id": "63f8a1b2c4d5e6f7g8h9i0j1",
    "title": "Introduction to Spring Boot",
    "content": "Spring Boot makes it easy to create stand-alone, production-grade Spring-based applications...",
    "author": "John Doe",
    "createdAt": "2026-01-30T16:30:00",
    "updatedAt": "2026-01-30T16:30:00"
  }
]
```

### 6. Get Documents by Author

**Request:**
```bash
curl http://localhost:8080/api/documents/author/John%20Doe
```

**Response (200 OK):**
```json
[
  {
    "id": "63f8a1b2c4d5e6f7g8h9i0j1",
    "title": "Introduction to Spring Boot",
    "content": "Spring Boot makes it easy to create stand-alone, production-grade Spring-based applications...",
    "author": "John Doe",
    "createdAt": "2026-01-30T16:30:00",
    "updatedAt": "2026-01-30T16:30:00"
  }
]
```

### 7. Update a Document

**Request:**
```bash
curl -X PUT http://localhost:8080/api/documents/63f8a1b2c4d5e6f7g8h9i0j1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Advanced Spring Boot",
    "content": "Spring Boot advanced topics include actuator, security, and microservices architecture.",
    "author": "John Doe"
  }'
```

**Response (200 OK):**
```json
{
  "id": "63f8a1b2c4d5e6f7g8h9i0j1",
  "title": "Advanced Spring Boot",
  "content": "Spring Boot advanced topics include actuator, security, and microservices architecture.",
  "author": "John Doe",
  "createdAt": "2026-01-30T16:30:00",
  "updatedAt": "2026-01-30T16:35:00"
}
```

### 8. Delete a Document

**Request:**
```bash
curl -X DELETE http://localhost:8080/api/documents/63f8a1b2c4d5e6f7g8h9i0j1
```

**Response (204 No Content):**
```
(empty body)
```

## Error Responses

### 400 Bad Request - Missing Required Fields

**Request:**
```bash
curl -X POST http://localhost:8080/api/documents \
  -H "Content-Type: application/json" \
  -d '{
    "title": ""
  }'
```

**Response (400 Bad Request):**
```
(empty body)
```

### 404 Not Found - Document Does Not Exist

**Request:**
```bash
curl http://localhost:8080/api/documents/nonexistentid123
```

**Response (404 Not Found):**
```
(empty body)
```

## Testing with Postman

1. Import the endpoints into Postman
2. Set the base URL to `http://localhost:8080`
3. For POST and PUT requests, set the Content-Type header to `application/json`
4. Use the JSON bodies shown in the examples above

## Notes

- All timestamps are in ISO 8601 format
- Document IDs are auto-generated by MongoDB
- Search is case-insensitive and matches both title and content fields
- The cache TTL is set to 10 minutes by default
- ElasticSearch provides full-text search with stemming and relevance scoring
