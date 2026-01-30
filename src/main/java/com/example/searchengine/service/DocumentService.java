package com.example.searchengine.service;

import com.example.searchengine.model.DocumentEntity;
import com.example.searchengine.model.SearchableDocument;
import com.example.searchengine.repository.elasticsearch.SearchableDocumentRepository;
import com.example.searchengine.repository.mongo.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final SearchableDocumentRepository searchableDocumentRepository;

    public DocumentEntity createDocument(DocumentEntity document) {
        log.info("Creating document: {}", document.getTitle());
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());
        
        // Save to MongoDB (primary storage)
        DocumentEntity savedDocument = documentRepository.save(document);
        
        // Index in Elasticsearch for search
        try {
            SearchableDocument searchableDocument = convertToSearchable(savedDocument);
            searchableDocumentRepository.save(searchableDocument);
        } catch (Exception e) {
            log.error("Failed to index document in ElasticSearch: {}", e.getMessage());
            // Document is saved in MongoDB but not indexed. Consider implementing retry logic.
        }
        
        log.info("Document created with id: {}", savedDocument.getId());
        return savedDocument;
    }

    @Cacheable(value = "documents", key = "#id")
    public Optional<DocumentEntity> getDocumentById(String id) {
        log.info("Fetching document by id: {}", id);
        return documentRepository.findById(id);
    }

    @Cacheable(value = "documents")
    public List<DocumentEntity> getAllDocuments() {
        log.info("Fetching all documents");
        return documentRepository.findAll();
    }

    @CacheEvict(value = {"documents", "search"}, allEntries = true)
    public DocumentEntity updateDocument(String id, DocumentEntity document) {
        log.info("Updating document with id: {}", id);
        return documentRepository.findById(id)
                .map(existingDoc -> {
                    existingDoc.setTitle(document.getTitle());
                    existingDoc.setContent(document.getContent());
                    existingDoc.setAuthor(document.getAuthor());
                    existingDoc.setUpdatedAt(LocalDateTime.now());
                    
                    DocumentEntity updated = documentRepository.save(existingDoc);
                    
                    // Update in Elasticsearch
                    try {
                        SearchableDocument searchableDocument = convertToSearchable(updated);
                        searchableDocumentRepository.save(searchableDocument);
                    } catch (Exception e) {
                        log.error("Failed to update document in ElasticSearch: {}", e.getMessage());
                        // Document is updated in MongoDB but not in ElasticSearch.
                    }
                    
                    return updated;
                })
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
    }

    @CacheEvict(value = {"documents", "search"}, allEntries = true)
    public void deleteDocument(String id) {
        log.info("Deleting document with id: {}", id);
        documentRepository.deleteById(id);
        try {
            searchableDocumentRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Failed to delete document from ElasticSearch: {}", e.getMessage());
            // Document is deleted from MongoDB but may still exist in ElasticSearch.
        }
    }

    @Cacheable(value = "search", key = "#query")
    public List<DocumentEntity> searchDocuments(String query) {
        log.info("Searching documents with query: {}", query);
        List<SearchableDocument> searchResults = 
                searchableDocumentRepository.findByTitleContainingOrContentContaining(query, query);
        
        // Fetch full documents from MongoDB based on search results
        List<String> ids = searchResults.stream()
                .map(SearchableDocument::getId)
                .collect(Collectors.toList());
        
        return StreamSupport.stream(documentRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<DocumentEntity> getDocumentsByAuthor(String author) {
        log.info("Fetching documents by author: {}", author);
        return documentRepository.findByAuthor(author);
    }

    private SearchableDocument convertToSearchable(DocumentEntity document) {
        SearchableDocument searchable = new SearchableDocument();
        searchable.setId(document.getId());
        searchable.setTitle(document.getTitle());
        searchable.setContent(document.getContent());
        searchable.setAuthor(document.getAuthor());
        searchable.setCreatedAt(document.getCreatedAt());
        searchable.setUpdatedAt(document.getUpdatedAt());
        return searchable;
    }
}
