package org.example.crawlercore.model;

public record CrawlJob(String url, int depth, int priority)
        implements Comparable<CrawlJob> {

    @Override
    public int compareTo(CrawlJob o) {
        return Integer.compare(this.priority, o.priority);
    }
}