package com.example.searchengine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "documents")
public class DocumentEntity {
    
    @Id
    private String id;
    
    private String title;
    
    private String content;
    
    private String author;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
