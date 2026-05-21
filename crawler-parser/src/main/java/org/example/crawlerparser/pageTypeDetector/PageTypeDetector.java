package org.example.crawlerparser.pageTypeDetector;

public interface PageTypeDetector {

    PageType getType(String url);
}
