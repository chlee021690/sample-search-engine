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
public class GetTextDocumentBySearchDto {
    private int numberOfResults;
    private String title;
    private String content;
}
