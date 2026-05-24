package org.example.crawlerparser.model;

import java.util.Optional;
import java.util.Set;

public record ParsingResult(Optional<ScrapeResponse> productScrapeResponse, Set<String> hrefs) {}
