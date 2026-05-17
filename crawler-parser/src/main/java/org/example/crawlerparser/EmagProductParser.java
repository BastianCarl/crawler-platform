package org.example.crawlerparser;

import java.net.URI;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.example.browserworkerclient.dto.FetchResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class EmagProductParser implements ProductParser {

    private static final String TITLE_SELECTOR = "h1.page-title";

    private static final String PRICE_SELECTOR = ".product-new-price";

    @Override
    public ProductScrapeResponse parse(FetchResult result) {
        Document doc = Jsoup.parse(result.html());
        String title = extractTitleFromUrl(result.url());
        String rawPrice = "price";
        return new ProductScrapeResponse(result.url(), title, rawPrice, "RON", true, null, null, "emag", Instant.now());
    }

    private String extractTitleFromUrl(String url) {
        URI uri = URI.create(url);
        String path = uri.getPath();
        return Arrays.stream(path.split("/"))
                .filter(segment -> !segment.isBlank())
                .map(segment -> segment.replace("-", " "))
                .collect(Collectors.joining(" "));
    }
}
