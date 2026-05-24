package org.example.crawlercore.service;

import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.example.crawlerparser.model.ProductMapper;
import org.example.crawlerparser.model.ProductScrapeResponse;
import org.example.crawlerparser.pageTypeDetector.EmagPageTypeDetector;
import org.example.crawlerparser.pageTypeDetector.PageType;
import org.example.crawlerparser.respository.ProductRepository;
import org.example.crawlerparser.service.ServiceParser;
import org.springframework.stereotype.Service;

@Service
public class CrawlService {

    private final ServiceParser serviceParser;
    private final InMemoryUrlFrontier urlFrontier;
    private final EmagUrlNormalizer normalizer;
    private final CrawlJobValidator crawlJobValidator;
    private final EmagPageTypeDetector pageTypeDetector;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private static final int MAX_DEPTH = 2;
    private static final Path VISITED_URLS_FILE = Paths.get("visited-urls2.txt");

    public CrawlService(
            ServiceParser serviceParser,
            InMemoryUrlFrontier urlFrontier,
            EmagUrlNormalizer normalizer,
            CrawlJobValidator crawlJobValidator,
            EmagPageTypeDetector pageTypeDetector,
            ProductRepository productRepository,
            ProductMapper productMapper) {
        this.serviceParser = serviceParser;
        this.urlFrontier = urlFrontier;
        this.normalizer = normalizer;
        this.crawlJobValidator = crawlJobValidator;
        this.pageTypeDetector = pageTypeDetector;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public void crawl(String rootUrl) {
        CrawlJob rootCrawlJob =
                new CrawlJob(normalizer.normalize(rootUrl, rootUrl).get(), 0, PageType.NON_PRODUCT);
        urlFrontier.push(rootCrawlJob);
        CrawlJob currentCrawlJob;
        while ((currentCrawlJob = urlFrontier.poll().orElse(null)) != null) {
            ParsingResult parsingResult = serviceParser.scrape(
                    currentCrawlJob.pageType(),
                    new FetcherRequest(
                            currentCrawlJob.url(),
                            Map.of(
                                    "Accept-Language", "en-US,en;q=0.9",
                                    "Cache-Control", "no-cache"),
                            Duration.of(3, ChronoUnit.MINUTES)));

            Set<String> hrefs = parsingResult.hrefs();
            Set<String> urls = normalizer.normalize(hrefs, currentCrawlJob.url());
            urls = crawlJobValidator.collectCrawlableUrls(currentCrawlJob, MAX_DEPTH, urls);
            int currentDepth = currentCrawlJob.depth();
            urls.forEach(url -> urlFrontier.push(new CrawlJob(url, currentDepth + 1, pageTypeDetector.getType(url))));
            if (parsingResult.productScrapeResponse().isPresent()
                    && parsingResult.productScrapeResponse().get() instanceof ProductScrapeResponse productResponse) {
                productRepository.save(productMapper.toEntity(productResponse));
            }
        }
    }
}
