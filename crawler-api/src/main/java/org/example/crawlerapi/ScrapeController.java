package org.example.crawlerapi;

import lombok.RequiredArgsConstructor;
import org.example.browserworkerclient.dto.ProductScrapeRequest;
import org.example.browserworkerclient.dto.ProductScrapeResponse;
import org.example.crawlerfetcher.ScrapeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/browser")
@RequiredArgsConstructor
public class ScrapeController {

    private final ScrapeService scrapeService;

    @PostMapping("/scrape")
    public ProductScrapeResponse scrape(@RequestBody ProductScrapeRequest request) {
        return scrapeService.scrape(request.url());
    }
}
