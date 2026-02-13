package com.chlee021690.sampleproject.searchengine.repository.implementations;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Repository;

import com.chlee021690.sampleproject.searchengine.dto.SearchTextDocumentsDto;
import com.chlee021690.sampleproject.searchengine.entity.TextDocument;
import com.chlee021690.sampleproject.searchengine.repository.interfaces.custom.CustomTextDocumentSearchRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomTextDocumentSerachRepositoryImpl implements CustomTextDocumentSearchRepository {

    private final ElasticsearchOperations elasticSearchOperations;

    @Override
    public List<TextDocument> searchDocuments(SearchTextDocumentsDto dto) {

        CriteriaQuery query = createSearchQuery(dto);
        SearchHits<TextDocument> searchHits = elasticSearchOperations.search(query, TextDocument.class);
        return searchHits.stream().map(SearchHit::getContent).toList();
    }

    private CriteriaQuery createSearchQuery(SearchTextDocumentsDto dto){

        return  new CriteriaQuery(
            new Criteria("title").contains(dto.getTitle())
            .or(new Criteria("content").contains(dto.getContent()))
        ).setPageable(PageRequest.of(0, dto.getNumberOfResults()));
    }

}
