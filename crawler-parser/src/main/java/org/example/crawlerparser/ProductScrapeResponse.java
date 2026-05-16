package org.example.crawlerparser;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductScrapeResponse(

        String url,

        String title,

        String price,

        String currency,

        boolean inStock,

        String imageUrl,

        String sku,

        String source,

        Instant scrapedAt

) {}