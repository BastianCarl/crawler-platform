package org.example.crawlercore.urlFrontier;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import org.example.crawlercore.model.CrawlJob;
import org.springframework.stereotype.Component;

@Component
public class InMemoryUrlFrontier implements UrlFrontier {
    private final Queue<CrawlJob> queue = new PriorityQueue<>();

    @Override
    public void push(CrawlJob crawlJob) {
        queue.add(crawlJob);
    }

    @Override
    public Optional<CrawlJob> poll() {
        return Optional.ofNullable(queue.poll());
    }
}
