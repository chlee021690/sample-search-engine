package com.chlee021690.sampleproject.searchengine.init;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import com.chlee021690.sampleproject.searchengine.entity.TextDocument;
import com.chlee021690.sampleproject.searchengine.repository.interfaces.main.TextDocumentSearchRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DocumentBootstrap implements ApplicationRunner {

    private final ElasticsearchOperations operations;
    private final TextDocumentSearchRepository repository;

    // public DocumentBootstrap(ElasticsearchOperations operations, DocumentRepository repository) {
    //     this.operations = operations;
    //     this.repository = repository;
    // }

    @Override
    public void run(ApplicationArguments args) {
        IndexOperations indexOps = operations.indexOps(TextDocument.class);

        if (!indexOps.exists()) {
            indexOps.create();
            indexOps.putMapping(indexOps.createMapping());
        }

        long count = operations.count(Query.findAll(), TextDocument.class);
        if (count > 0) {
            return; // 이미 업로드됨
        }

        List<TextDocument> seedDocs = new ArrayList<>();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            Resource[] resources = resolver.getResources("classpath:seed/doc/*.txt");

            for (Resource resource : resources) {
                String filename = resource.getFilename();
                if (filename == null) {
                    continue;
                }

                String baseName = filename.replaceAll("\\.txt$", "");
                String id = baseName;
                String title = baseName;
                int separatorIndex = baseName.indexOf('-');
                if (separatorIndex > 0 && separatorIndex < baseName.length() - 1) {
                    id = baseName.substring(0, separatorIndex);
                    title = baseName.substring(separatorIndex + 1);
                }

                try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                    String content = reader.lines().collect(Collectors.joining("\n")).trim();

                    seedDocs.add(
                        TextDocument.builder()
                            .id(id)
                            .title(title)
                            .content(content)
                            .build()
                    );
                }
            }

            if (!seedDocs.isEmpty()) {
                repository.saveAll(seedDocs);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load seed text documents", e);
        }
    }
}
