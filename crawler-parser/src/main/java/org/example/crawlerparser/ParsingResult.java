package org.example.crawlerparser;

import java.util.Set;

public record ParsingResult(
        ProductScrapeResponse productScrapeResponse,
        Set<String> links
) {}