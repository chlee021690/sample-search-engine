# Search Engine (Spring Boot + Elasticsearch)

Sample text search engine built with Spring Boot and Elasticsearch. The app seeds documents at startup and uses Spring Data Elasticsearch repositories to manage the search index.

## Features
- Spring Boot 4 + Spring Data Elasticsearch
- Seed documents loaded on application startup
- Local development with Elasticsearch + Redis (Compose)
- GraphQL client code generation (DGS Codegen)

## Requirements
- Java 21
- Gradle 8+
- Elasticsearch (local or Elastic Cloud/Found)

## Configuration
The following environment variables are optional and fall back to local defaults:

- FOUNDELASTICSEARCH_URL (default: http://localhost:9200)
- ELASTIC_USERNAME (default: empty)
- ELASTIC_PASSWORD (default: empty)

These map to the Spring properties in application.yaml:
- spring.elasticsearch.uris
- spring.elasticsearch.username
- spring.elasticsearch.password

## Running
Local run (with Elasticsearch/Redis running locally):
- ./gradlew bootRun

Elastic Cloud/Found run:
1) Set environment variables
2) ./gradlew bootRun

## Seed Data
On startup, the app reads src/main/resources/seed/doc/*.txt and indexes the documents.

## Index Name
Elasticsearch index names must be lowercase. The current index name is document.

## GraphQL Codegen
Place GraphQL schemas in src/main/resources/graphql-client/ to use DGS Codegen.

## Docker Compose
compose.yaml defines Elasticsearch and Redis for local development.

## References
- Spring Data Elasticsearch: https://docs.spring.io/spring-boot/4.0.2/reference/data/nosql.html#data.nosql.elasticsearch
- Elasticsearch: https://docs.spring.io/spring-boot/4.0.2/reference/data/nosql.html#data.nosql.elasticsearch
- Gradle: https://docs.gradle.org
