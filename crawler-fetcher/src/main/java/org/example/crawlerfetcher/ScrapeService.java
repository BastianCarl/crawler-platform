package org.example.crawlerfetcher;

import lombok.RequiredArgsConstructor;
import org.example.browserworkerclient.BrowserWorkerClient;
import org.example.browserworkerclient.dto.ProductScrapeResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScrapeService {

    private final BrowserWorkerClient browserWorkerClient;

    public ProductScrapeResponse scrape(String url) {
        return browserWorkerClient.scrape(url);
    }
}
