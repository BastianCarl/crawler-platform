package org.example.crawlerapi;

import org.example.browserworkerclient.dto.FetcherRequest;
import org.example.crawlercore.CrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScrapeService {

    private final CrawlService crawlService;

    @Autowired
    public ScrapeService(CrawlService crawlService) {
        this.crawlService = crawlService;
    }

    public void scrape(FetcherRequest fetcherRequest) {
        crawlService.crawl(fetcherRequest.url());
    }
}
