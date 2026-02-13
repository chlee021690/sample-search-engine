package com.chlee021690.sampleproject.searchengine.payload.request;

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
public class SearchTextDocumentsRequestBody {
    private String textInput;
    private int contentSizeLimit;
}
