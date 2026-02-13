package com.chlee021690.sampleproject.searchengine.repository.interfaces.main;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.chlee021690.sampleproject.searchengine.entity.TextDocument;
import com.chlee021690.sampleproject.searchengine.repository.interfaces.custom.CustomTextDocumentSearchRepository;

@Repository
public interface TextDocumentSearchRepository extends ElasticsearchRepository<TextDocument, String>, CustomTextDocumentSearchRepository {

}
