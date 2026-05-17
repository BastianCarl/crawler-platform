package org.example.crawlerparser.model;

import java.util.Set;

public record ParsingResult(ProductScrapeResponse productScrapeResponse, Set<String> links) {}
