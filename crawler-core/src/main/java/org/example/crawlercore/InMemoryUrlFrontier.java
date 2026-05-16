package org.example.crawlercore;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.stereotype.Component;

@Component
public class InMemoryUrlFrontier implements UrlFrontier {
    private final Queue<CrawlJob> queue = new ConcurrentLinkedQueue<>();

    @Override
    public void push(CrawlJob crawlJob) {
        queue.add(crawlJob);
    }

    @Override
    public Optional<CrawlJob> poll() {
        return Optional.ofNullable(queue.poll());
    }
}
