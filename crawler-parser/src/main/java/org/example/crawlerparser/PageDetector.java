package org.example.crawlerparser;

import org.example.browserworkerclient.dto.FetchResult;

public interface PageDetector {

    PageType getType(FetchResult result);
}