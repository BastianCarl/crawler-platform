package org.example.crawlerparser.model;

public sealed interface ScrapeResponse permits ProductScrapeResponse, NonProductScrapeResponse {}