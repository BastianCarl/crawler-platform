package org.example.crawlerparser.pageTypeDetector;

public enum PageType {
    PRODUCT(0),
    NON_PRODUCT(1);

    PageType(int priority) {
        this.priority = priority;
    }

    public final int priority;
}
