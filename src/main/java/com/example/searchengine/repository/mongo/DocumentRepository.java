package com.example.searchengine.repository.mongo;

import com.example.searchengine.model.DocumentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends MongoRepository<DocumentEntity, String> {
    
    List<DocumentEntity> findByAuthor(String author);
    
    List<DocumentEntity> findByTitleContaining(String title);
}
