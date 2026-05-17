package org.example.crawlercore.urlDeduplicator;

public interface UrlDeduplicator {
    boolean shouldVisit(String url);
}
