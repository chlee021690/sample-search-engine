package com.chlee021690.sampleproject.searchengine.repository.implementations;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.chlee021690.sampleproject.searchengine.dto.SearchTextDocumentsDto;
import com.chlee021690.sampleproject.searchengine.entity.TextDocument;
import com.chlee021690.sampleproject.searchengine.repository.CustomTextDocumentSearchRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomTextDocumentSearchRepositoryImpl implements CustomTextDocumentSearchRepository {

    private final ElasticsearchOperations elasticSearchOperations;

    @Override
    public List<TextDocument> searchDocuments(SearchTextDocumentsDto dto) {
        CriteriaQuery query = createSearchQuery(dto);
        SearchHits<TextDocument> searchHits = elasticSearchOperations.search(query, TextDocument.class);
        return searchHits.stream().map(SearchHit::getContent).toList();
    }

    private CriteriaQuery createSearchQuery(SearchTextDocumentsDto dto) {
        Criteria criteria = buildCriteria(dto);
        return createCriteriaQuery(criteria, dto.getNumberOfResults());
    }

    private CriteriaQuery createCriteriaQuery(Criteria criteria, int numberOfResults) {
        CriteriaQuery query = new CriteriaQuery(criteria);
        if (numberOfResults > 0) {
            query.setPageable(PageRequest.of(0, numberOfResults));
        }
        return query;
    }

    private Criteria buildCriteria(SearchTextDocumentsDto dto) {
        if (dto == null) {
            return new Criteria();
        }

        Criteria criteria = null;

        if (StringUtils.hasText(dto.getTitle())) {
            criteria = new Criteria("title").contains(dto.getTitle());
        }

        if (StringUtils.hasText(dto.getContent())) {
            Criteria contentCriteria = new Criteria("content").contains(dto.getContent());
            criteria = (criteria == null) ? contentCriteria : criteria.or(contentCriteria);
        }

        return (criteria == null) ? new Criteria() : criteria;
    }

    // private CriteriaQuery createSearchQuery(SearchTextDocumentsDto dto){
    //     return  new CriteriaQuery(
    //         new Criteria("title").contains(dto.getTitle())
    //         .or(new Criteria("content").contains(dto.getContent()))
    //     ).setPageable(PageRequest.of(0, dto.getNumberOfResults()));
    // }
}