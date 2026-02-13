package com.chlee021690.sampleproject.searchengine.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.chlee021690.sampleproject.searchengine.entity.TextDocument;

@Repository
public interface TextDocumentSearchRepository extends ElasticsearchRepository<TextDocument, String> {

}
