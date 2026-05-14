package org.example.crawlerfetcher;

import lombok.RequiredArgsConstructor;
import org.example.browserworkerclient.dto.ProductScrapeRequest;
import org.example.browserworkerclient.dto.ProductScrapeResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class ScrapeService {
    private final RestClient restClient;
    private static final String BASE_BROWSER_URI = "/browser";
    private static final String SCRAPE_URI = BASE_BROWSER_URI + "/scrape";

    public ProductScrapeResponse scrape(String url) {
        return restClient
                .post()
                .uri(SCRAPE_URI)
                .body(new ProductScrapeRequest(url))
                .retrieve()
                .body(ProductScrapeResponse.class);
        }
}
