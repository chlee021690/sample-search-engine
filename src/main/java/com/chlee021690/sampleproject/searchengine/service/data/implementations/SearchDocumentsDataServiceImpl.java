package com.chlee021690.sampleproject.searchengine.service.data.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chlee021690.sampleproject.searchengine.dto.SearchTextDocumentsDto;
import com.chlee021690.sampleproject.searchengine.entity.TextDocument;
import com.chlee021690.sampleproject.searchengine.repository.TextDocumentSearchRepository;
import com.chlee021690.sampleproject.searchengine.service.data.interfaces.SearchDocumentsDataService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchDocumentsDataServiceImpl implements SearchDocumentsDataService {
    private final TextDocumentSearchRepository textDocumentSearchRepository;
    
    @Override
    public List<TextDocument> searchDouments(SearchTextDocumentsDto dto) {
        return textDocumentSearchRepository.searchDocuments(dto);
    }
}
