package com.chlee021690.sampleproject.searchengine.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.chlee021690.sampleproject.searchengine.entity.TextDocument;

public interface TextDocumentRepository extends ElasticsearchRepository<TextDocument, String> {

}
