package com.example.searchengine.controller;

import com.example.searchengine.model.DocumentEntity;
import com.example.searchengine.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public ResponseEntity<DocumentEntity> createDocument(@RequestBody DocumentEntity document) {
        if (document == null || document.getTitle() == null || document.getTitle().trim().isEmpty() ||
            document.getContent() == null || document.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        DocumentEntity created = documentService.createDocument(document);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentEntity> getDocument(@PathVariable String id) {
        return documentService.getDocumentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DocumentEntity>> getAllDocuments() {
        List<DocumentEntity> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentEntity> updateDocument(
            @PathVariable String id,
            @RequestBody DocumentEntity document) {
        if (document == null || document.getTitle() == null || document.getTitle().trim().isEmpty() ||
            document.getContent() == null || document.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            DocumentEntity updated = documentService.updateDocument(id, document);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<DocumentEntity>> searchDocuments(@RequestParam String query) {
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<DocumentEntity> results = documentService.searchDocuments(query);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<DocumentEntity>> getDocumentsByAuthor(@PathVariable String author) {
        List<DocumentEntity> documents = documentService.getDocumentsByAuthor(author);
        return ResponseEntity.ok(documents);
    }
}
