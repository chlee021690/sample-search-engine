package com.chlee021690.sampleproject.searchengine.service.response.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chlee021690.sampleproject.searchengine.entity.TextDocument;
import com.chlee021690.sampleproject.searchengine.payload.response.TextDocumentsResponseEntity;
import com.chlee021690.sampleproject.searchengine.service.response.interfaces.SearchDocumentsResponseService;

@Service
public class SearchDocumentsResponseServiceImpl implements SearchDocumentsResponseService {

    @Override
    public List<TextDocumentsResponseEntity> getSearchDocumentsResponseEntities(List<TextDocument> textDocuments, int contentSizeLimit) {
        return textDocuments.stream()
                    .map(doc -> TextDocumentsResponseEntity.builder()
                        .id(doc.getId())
                        .fullTitle(doc.getTitle())
                        .partialContent(doc.getContent().length() > contentSizeLimit ? doc.getContent().substring(0, contentSizeLimit) + "..." : doc.getContent())
                        .build())
                    .toList();
    }

    

}
