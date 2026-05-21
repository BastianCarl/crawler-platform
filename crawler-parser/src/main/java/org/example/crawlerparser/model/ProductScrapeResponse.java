package org.example.crawlerparser.model;

import java.time.Instant;

public record ProductScrapeResponse(
        String url,
        String title,
        String price,
        String currency,
        boolean inStock,
        String source,
        Instant scrapedAt)
{
    @Override
    public String toString() {
        return """
            ProductScrapeResponse {
                url='%s',
                title='%s',
                price='%s %s',
                inStock=%s,
                source='%s',
                scrapedAt=%s
            }
            """.formatted(
                url,
                title,
                price,
                currency,
                inStock,
                source,
                scrapedAt
        );
    }
}

