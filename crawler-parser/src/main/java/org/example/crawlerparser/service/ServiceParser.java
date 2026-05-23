package org.example.crawlerparser.service;

import java.util.Set;
import org.example.browserworkerclient.dto.FetchResult;
import org.example.browserworkerclient.dto.FetcherRequest;
import org.example.crawlerfetcher.FetchService;
import org.example.crawlerparser.hrefExtractor.EmagHrefExtractor;
import org.example.crawlerparser.model.ParsingResult;
import org.example.crawlerparser.model.ProductScrapeResponse;
import org.example.crawlerparser.model.ScrapeResponse;
import org.example.crawlerparser.pageTypeDetector.PageType;
import org.example.crawlerparser.productParser.EmagProductParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceParser {

    private final FetchService fetchService;
    private final EmagProductParser emagProductParser;
    private final EmagHrefExtractor emagLinkExtractor;

    @Autowired
    public ServiceParser(
            FetchService fetchService, EmagProductParser emagProductParser, EmagHrefExtractor emagLinkExtractor) {
        this.fetchService = fetchService;
        this.emagProductParser = emagProductParser;
        this.emagLinkExtractor = emagLinkExtractor;
    }

    public ParsingResult scrape(PageType pageType, FetcherRequest fetcherRequest) {
        FetchResult fetchResult = fetchService.fetch(fetcherRequest);
        ScrapeResponse scrapeResponse = emagProductParser.parse(pageType, fetchResult);
        Set<String> href = emagLinkExtractor.extract(fetchResult);
        return new ParsingResult(scrapeResponse, href);
    }
}
