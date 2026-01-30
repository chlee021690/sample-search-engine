package com.example.searchengine.repository.elasticsearch;

import com.example.searchengine.model.SearchableDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchableDocumentRepository extends ElasticsearchRepository<SearchableDocument, String> {
    
    List<SearchableDocument> findByTitle(String title);
    
    List<SearchableDocument> findByAuthor(String author);
    
    List<SearchableDocument> findByTitleContainingOrContentContaining(String title, String content);
}
