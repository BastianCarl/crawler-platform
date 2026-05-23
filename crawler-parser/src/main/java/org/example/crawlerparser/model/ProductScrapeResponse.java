package org.example.crawlerparser.model;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductScrapeResponse(
        String url,
        String title,
        BigDecimal price,
        String currency,
        boolean inStock,
        String source,
        Instant scrapedAt
) implements ScrapeResponse {}
