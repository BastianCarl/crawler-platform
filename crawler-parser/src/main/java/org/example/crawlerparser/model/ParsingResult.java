package org.example.crawlerparser.model;

import java.util.Set;

public record ParsingResult(ScrapeResponse productScrapeResponse, Set<String> links) {}
