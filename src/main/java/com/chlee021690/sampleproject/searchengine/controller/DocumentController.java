package com.chlee021690.sampleproject.searchengine.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chlee021690.sampleproject.searchengine.dto.SearchTextDocumentsDto;
import com.chlee021690.sampleproject.searchengine.entity.TextDocument;
import com.chlee021690.sampleproject.searchengine.payload.request.SearchTextDocumentsRequestBody;
import com.chlee021690.sampleproject.searchengine.payload.response.TextDocumentsResponseEntity;
import com.chlee021690.sampleproject.searchengine.service.data.interfaces.SearchDocumentsDataService;
import com.chlee021690.sampleproject.searchengine.service.response.interfaces.SearchDocumentsResponseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final SearchDocumentsDataService searchDocumentsDataService;
    private final SearchDocumentsResponseService searchDocumentsResponseService;

    @PostMapping("")
    public ResponseEntity<List<TextDocumentsResponseEntity>> searchDocuments(
        @RequestParam("numberOfResults") int numberOfResults,
        @RequestBody SearchTextDocumentsRequestBody request
    ){
        // Implementation goes here
        SearchTextDocumentsDto dtoObject = SearchTextDocumentsDto.builder()
            .title(request.getTextInput())
            .content(request.getTextInput())
            .numberOfResults(numberOfResults)
            .contentSizeLimit(request.getContentSizeLimit())
            .build();

        List<TextDocument> foundDocuments = searchDocumentsDataService.searchDouments(dtoObject);
        List<TextDocumentsResponseEntity> responseEntities = searchDocumentsResponseService.getSearchDocumentsResponseEntities(foundDocuments, dtoObject.getContentSizeLimit());
        return ResponseEntity.ok(responseEntities);
    }

}
