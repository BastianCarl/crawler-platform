package org.example.crawlerapi;

import org.example.browserworkerclient.dto.FetchResult;
import org.example.browserworkerclient.dto.FetcherRequest;
import org.example.crawlerfetcher.FetchService;
import org.example.crawlerparser.EmagLinkExtractor;
import org.example.crawlerparser.EmagProductParser;
import org.example.crawlerparser.ProductScrapeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ScrapeService {

    private final FetchService fetchService;
    private final EmagProductParser emagProductParser;
    private final EmagLinkExtractor emagLinkExtractor;

    @Autowired
    public ScrapeService(FetchService fetchService, EmagProductParser emagProductParser, EmagLinkExtractor emagLinkExtractor) {
        this.fetchService = fetchService;
        this.emagProductParser = emagProductParser;
        this.emagLinkExtractor = emagLinkExtractor;
    }

    public FetchResult scrape(FetcherRequest fetcherRequest) {
        FetchResult fetchResult = fetchService.fetch(fetcherRequest);
        ProductScrapeResponse productScrapeResponse =  emagProductParser.parse(fetchResult);
        Set<String> links = emagLinkExtractor.extract(fetchResult);
        return null;
    }
}


