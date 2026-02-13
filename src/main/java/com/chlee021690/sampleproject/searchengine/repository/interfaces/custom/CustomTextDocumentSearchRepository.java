package com.chlee021690.sampleproject.searchengine.repository.interfaces.custom;

import java.util.List;

import com.chlee021690.sampleproject.searchengine.dto.GetTextDocumentBySearchDto;
import com.chlee021690.sampleproject.searchengine.entity.TextDocument;

public interface CustomTextDocumentSearchRepository {

    public List<TextDocument> searchDocuments(GetTextDocumentBySearchDto text);
}
