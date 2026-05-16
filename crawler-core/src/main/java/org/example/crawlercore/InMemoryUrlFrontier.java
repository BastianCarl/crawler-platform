package org.example.crawlercore;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

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