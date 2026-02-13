package com.chlee021690.sampleproject.searchengine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchTextDocumentsDto {
    private int numberOfResults;
    private String title;
    private String content;
    private int contentSizeLimit;
}
