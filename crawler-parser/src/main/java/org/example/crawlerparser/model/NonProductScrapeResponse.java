package org.example.crawlerparser.model;

import java.time.Instant;

public record NonProductScrapeResponse(String url, String title, String source, Instant scrapedAt)
        implements ScrapeResponse {
    @Override
    public String toString() {
        return """
            NonProductScrapeResponse {
                url='%s',
                title='%s',
                source='%s',
                scrapedAt=%s
            }
            """
                .formatted(url, title, source, scrapedAt);
    }
}
