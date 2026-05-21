package org.example.crawlercore.model;

import org.example.crawlerparser.pageTypeDetector.PageType;

public record CrawlJob(String url, int depth, PageType pageType)
        implements Comparable<CrawlJob> {

    @Override
    public int compareTo(CrawlJob o) {
        return Integer.compare(this.pageType.priority, o.pageType.priority);
    }
}