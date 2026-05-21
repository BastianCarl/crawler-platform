package org.example.crawlercore.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Set;
import org.example.browserworkerclient.dto.FetcherRequest;
import org.example.crawlercore.model.CrawlJob;
import org.example.crawlercore.urlFrontier.InMemoryUrlFrontier;
import org.example.crawlercore.urlNormalizer.EmagUrlNormalizer;
import org.example.crawlercore.validator.CrawlJobValidator;
import org.example.crawlerparser.model.ParsingResult;
import org.example.crawlerparser.model.ProductScrapeResponse;
import org.example.crawlerparser.pageTypeDetector.EmagPageTypeDetector;
import org.example.crawlerparser.pageTypeDetector.PageType;
import org.example.crawlerparser.service.ServiceParser;
import org.springframework.stereotype.Service;

@Service
public class CrawlService {

    private final ServiceParser serviceParser;
    private final InMemoryUrlFrontier urlFrontier;
    private final EmagUrlNormalizer normalizer;
    private final CrawlJobValidator crawlJobValidator;
    private final EmagPageTypeDetector pageTypeDetector;
    private static final int MAX_DEPTH = 2;
    private static final Path VISITED_URLS_FILE = Paths.get("visited-urls2.txt");


    public CrawlService(ServiceParser serviceParser,
                        InMemoryUrlFrontier urlFrontier,
                        EmagUrlNormalizer normalizer,
                        CrawlJobValidator crawlJobValidator,
                        EmagPageTypeDetector pageTypeDetector
    ) {
        this.serviceParser = serviceParser;
        this.urlFrontier = urlFrontier;
        this.normalizer = normalizer;
        this.crawlJobValidator = crawlJobValidator;
        this.pageTypeDetector = pageTypeDetector;
    }

    public void crawl(String rootUrl) {
        CrawlJob rootCrawlJob = new CrawlJob(normalizer.normalize(rootUrl, rootUrl), 0, PageType.NON_PRODUCT);
        urlFrontier.push(rootCrawlJob);
        CrawlJob currentCrawlJob;
        while ((currentCrawlJob = urlFrontier.poll().orElse(null)) != null) {
            ParsingResult parsingResult = serviceParser.scrape(currentCrawlJob.pageType(),
                    new FetcherRequest(
                    currentCrawlJob.url(),
                    Map.of(
                            "Accept-Language", "en-US,en;q=0.9",
                            "Cache-Control", "no-cache"),
                    Duration.of(3, ChronoUnit.MINUTES)));

            Set<String> links = parsingResult.links();
            links = normalizer.normalize(links, currentCrawlJob.url());
            links = crawlJobValidator.collectCrawlableUrls(currentCrawlJob, MAX_DEPTH, links);
            int currentDepth = currentCrawlJob.depth();
            links.forEach(url -> urlFrontier.push(new CrawlJob(url, currentDepth + 1, pageTypeDetector.getType(url))));
            saveVisitedUrl(parsingResult.productScrapeResponse());
        }
    }

    private void saveVisitedUrl(ProductScrapeResponse response) {
        try {
            Files.writeString(
                    VISITED_URLS_FILE,
                    response.toString() + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
