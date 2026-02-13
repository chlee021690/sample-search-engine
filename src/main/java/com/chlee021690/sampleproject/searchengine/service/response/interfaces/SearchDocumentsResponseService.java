package com.chlee021690.sampleproject.searchengine.service.response.interfaces;

import java.util.List;

import com.chlee021690.sampleproject.searchengine.entity.TextDocument;
import com.chlee021690.sampleproject.searchengine.payload.response.TextDocumentsResponseEntity;

public interface SearchDocumentsResponseService {
    public List<TextDocumentsResponseEntity> getSearchDocumentsResponseEntities(List<TextDocument> textDocuments, int contentSizeLimit);
}
