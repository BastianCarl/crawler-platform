package org.example.crawlerparser.productParser;

import org.example.browserworkerclient.dto.FetchResult;

public interface ProductParser<T> {
    T parse(FetchResult fetchResult);
}
