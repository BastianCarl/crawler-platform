package org.example.crawlerparser;

import java.util.Set;
import org.example.browserworkerclient.dto.FetchResult;
import org.example.browserworkerclient.dto.FetcherRequest;
import org.example.crawlerfetcher.FetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceParser {

    private final FetchService fetchService;
    private final EmagProductParser emagProductParser;
    private final EmagLinkExtractor emagLinkExtractor;

    @Autowired
    public ServiceParser(
            FetchService fetchService, EmagProductParser emagProductParser, EmagLinkExtractor emagLinkExtractor) {
        this.fetchService = fetchService;
        this.emagProductParser = emagProductParser;
        this.emagLinkExtractor = emagLinkExtractor;
    }

    public ParsingResult scrape(FetcherRequest fetcherRequest) {
        FetchResult fetchResult = fetchService.fetch(fetcherRequest);
        ProductScrapeResponse productScrapeResponse = emagProductParser.parse(fetchResult);
        Set<String> href = emagLinkExtractor.extract(fetchResult);
        return new ParsingResult(productScrapeResponse, href);
    }
}
