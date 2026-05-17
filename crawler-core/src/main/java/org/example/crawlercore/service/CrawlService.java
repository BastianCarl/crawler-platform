package org.example.crawlercore.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;
import org.example.browserworkerclient.dto.FetcherRequest;
import org.example.crawlercore.model.CrawlJob;
import org.example.crawlercore.urlDeduplicator.InMemoryUrlDeduplicator;
import org.example.crawlercore.urlFilter.EmagUrlFilter;
import org.example.crawlercore.urlFrontier.InMemoryUrlFrontier;
import org.example.crawlercore.urlNormalizer.EmagUrlNormalizer;
import org.example.crawlerparser.model.ParsingResult;
import org.example.crawlerparser.service.ServiceParser;
import org.springframework.stereotype.Service;

@Service
public class CrawlService {

    private final ServiceParser serviceParser;
    private final InMemoryUrlFrontier urlFrontier;
    private final InMemoryUrlDeduplicator urlDeduplicator;
    private final EmagUrlNormalizer normalizer;
    private final EmagUrlFilter urlFilter;
    private static final int MAX_DEPTH = 2;

    private static final Path VISITED_URLS_FILE = Paths.get("visited-urls2.txt");

    public CrawlService(
            ServiceParser serviceParser,
            InMemoryUrlFrontier urlFrontier,
            InMemoryUrlDeduplicator urlDeduplicator,
            EmagUrlNormalizer normalizer,
            EmagUrlFilter urlFilter) {
        this.serviceParser = serviceParser;
        this.urlFrontier = urlFrontier;
        this.urlDeduplicator = urlDeduplicator;
        this.normalizer = normalizer;
        this.urlFilter = urlFilter;
    }

    public void crawl(String rootUrl) {
        CrawlJob crawlJob = new CrawlJob(normalizer.normalize(rootUrl, rootUrl), 0);
        urlFrontier.push(crawlJob);

        while (true) {

            Optional<CrawlJob> nextCrawlJob = urlFrontier.poll();

            if (nextCrawlJob.isEmpty()) {
                continue;
            } else {
                if (nextCrawlJob.get().url() == null) {
                    continue;
                }
            }

            if (nextCrawlJob.get().depth() > MAX_DEPTH) {
                break;
            }

            if (!urlFilter.shouldVisit(nextCrawlJob.get().url())) {
                continue;
            }

            String currentUrl = nextCrawlJob.get().url();

            if (!urlDeduplicator.shouldVisit(currentUrl)) {
                continue;
            }

            ParsingResult parsingResult = serviceParser.scrape(new FetcherRequest(
                    currentUrl,
                    Map.of(
                            "Accept-Language", "en-US,en;q=0.9",
                            "Cache-Control", "no-cache"),
                    Duration.of(3, ChronoUnit.MINUTES)));

            parsingResult.links().stream()
                    .map(href -> normalizer.normalize(currentUrl, href))
                    .forEach(url -> urlFrontier.push(
                            new CrawlJob(url, nextCrawlJob.get().depth() + 1)));

            saveVisitedUrl(currentUrl);
        }
    }

    private void saveVisitedUrl(String url) {
        try {
            Files.writeString(
                    VISITED_URLS_FILE,
                    url + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
