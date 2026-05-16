package org.example.crawlerparse;

import org.example.browserworkerclient.dto.FetchResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class EmagParser implements Parser {

    private static final String TITLE_SELECTOR =
            "h1.page-title";

    private static final String PRICE_SELECTOR =
            ".product-new-price";

    @Override
    public ProductScrapeResponse parse(FetchResult result) {

        Document doc = Jsoup.parse(result.html());

        String title = doc.select(TITLE_SELECTOR).text();

        String rawPrice = doc.select(PRICE_SELECTOR).text();

        String normalizedPrice = rawPrice
                .replace(".", "")
                .replace(",", ".")
                .replaceAll("[^0-9.]", "");

        BigDecimal price = new BigDecimal(normalizedPrice);

        return new ProductScrapeResponse(
                result.url(),
                title,
                price,
                "RON",
                true,
                null,
                null,
                "emag",
                Instant.now()
        );
    }
}