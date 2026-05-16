package org.example.crawlerparser;

import org.example.browserworkerclient.dto.FetchResult;

public interface ProductParser<T> {
    T parse(FetchResult fetchResult);
}
