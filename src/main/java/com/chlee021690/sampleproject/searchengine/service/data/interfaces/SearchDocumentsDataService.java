package com.chlee021690.sampleproject.searchengine.service.data.interfaces;

import java.util.List;

import com.chlee021690.sampleproject.searchengine.dto.SearchTextDocumentsDto;
import com.chlee021690.sampleproject.searchengine.entity.TextDocument;

public interface SearchDocumentsDataService {
    public List<TextDocument> searchDouments(SearchTextDocumentsDto dto);
}
