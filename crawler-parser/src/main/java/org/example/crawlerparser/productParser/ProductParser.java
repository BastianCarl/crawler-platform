package org.example.crawlerparser.productParser;

import org.example.browserworkerclient.dto.FetchResult;
import org.example.crawlerparser.pageTypeDetector.PageType;

public interface ProductParser<T> {
    T parse(PageType pageType, FetchResult fetchResult);
}
