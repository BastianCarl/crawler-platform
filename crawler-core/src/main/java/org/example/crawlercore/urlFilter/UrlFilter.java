package org.example.crawlercore.urlFilter;

public interface UrlFilter {
    boolean shouldVisit(String url);
}
