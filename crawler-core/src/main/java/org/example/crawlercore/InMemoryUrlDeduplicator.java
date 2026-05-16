package org.example.crawlercore;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryUrlDeduplicator {

    private final Set<String> visitedUrls = ConcurrentHashMap.newKeySet();

    public boolean shouldVisit(String url) {

        return visitedUrls.add(url);
    }
}