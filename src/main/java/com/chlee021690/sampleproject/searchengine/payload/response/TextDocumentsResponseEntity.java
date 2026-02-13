package com.chlee021690.sampleproject.searchengine.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TextDocumentsResponseEntity {
    private String id;
    private String fullTitle;
    private String partialContent;
}
