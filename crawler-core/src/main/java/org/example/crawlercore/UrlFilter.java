package org.example.crawlercore;

public interface UrlFilter {
    boolean shouldVisit(String url);
}