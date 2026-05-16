package org.example.crawlerparse;

import org.example.browserworkerclient.dto.FetchResult;

public interface Parser<T> {
    T parse(FetchResult fetchResult);
}