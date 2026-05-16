package org.example.crawlercore;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class InMemoryUrlDeduplicator {

    private final Set<String> visitedUrls = ConcurrentHashMap.newKeySet();

    public boolean shouldVisit(String url) {

        return visitedUrls.add(url);
    }
}
