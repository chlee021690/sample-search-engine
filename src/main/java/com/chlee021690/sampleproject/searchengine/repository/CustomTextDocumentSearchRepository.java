package com.chlee021690.sampleproject.searchengine.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.chlee021690.sampleproject.searchengine.dto.SearchTextDocumentsDto;
import com.chlee021690.sampleproject.searchengine.entity.TextDocument;

@Repository
public interface CustomTextDocumentSearchRepository {
    public List<TextDocument> searchDocuments(SearchTextDocumentsDto dto);
}
