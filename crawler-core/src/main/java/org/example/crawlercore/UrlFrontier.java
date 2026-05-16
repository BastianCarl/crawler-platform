package org.example.crawlercore;

import java.util.Optional;

public interface UrlFrontier {
    void push(CrawlJob crawlJob);
    Optional<CrawlJob> poll();
}