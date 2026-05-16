package org.example.crawlerparse;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductScrapeResponse(

        String url,

        String title,

        BigDecimal price,

        String currency,

        boolean inStock,

        String imageUrl,

        String sku,

        String source,

        Instant scrapedAt

) {}