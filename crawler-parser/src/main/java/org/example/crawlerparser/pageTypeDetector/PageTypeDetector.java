package org.example.crawlerparser.pageTypeDetector;

import org.example.browserworkerclient.dto.FetchResult;

public interface PageTypeDetector {

    PageType getType(FetchResult result);
}
