package org.example.crawlercore.urlFrontier;

import java.util.Optional;
import org.example.crawlercore.model.CrawlJob;

public interface UrlFrontier {
    void push(CrawlJob crawlJob);

    Optional<CrawlJob> poll();
}
