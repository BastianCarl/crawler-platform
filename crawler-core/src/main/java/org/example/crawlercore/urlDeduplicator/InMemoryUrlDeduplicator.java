package org.example.crawlercore.urlDeduplicator;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class InMemoryUrlDeduplicator implements UrlDeduplicator {

    private final Set<String> visitedUrls = ConcurrentHashMap.newKeySet();

    public boolean shouldVisit(String url) {
        return visitedUrls.add(url);
    }
}
