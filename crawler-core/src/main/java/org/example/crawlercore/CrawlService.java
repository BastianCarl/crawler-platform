package org.example.crawlercore;

import org.example.browserworkerclient.dto.FetcherRequest;
import org.example.crawlerparser.ParsingResult;
import org.example.crawlerparser.ServiceParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class CrawlService {

    private final ServiceParser serviceParser;
    private Set<String> urlsToVisit = new HashSet<>();

    @Autowired
    public CrawlService(ServiceParser serviceParser) {
        this.serviceParser = serviceParser;
    }

    public void crawl(String rootU) {
        urlsToVisit.add(rootU);
        while (!urlsToVisit.isEmpty()) {
            String currentUrl = urlsToVisit.iterator().next();
            ParsingResult parsingResult =  serviceParser.scrape(
                    new FetcherRequest(currentUrl,
                            Map.of(
                                    "Accept-Language", "en-US,en;q=0.9",
                                    "Cache-Control", "no-cache"
                           ),
                            Duration.of(3, ChronoUnit.MINUTES)));
            urlsToVisit.remove(currentUrl);
            urlsToVisit.addAll(parsingResult.links());
        }
    }
}
