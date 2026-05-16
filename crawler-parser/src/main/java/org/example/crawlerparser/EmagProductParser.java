package org.example.crawlerparser;

import org.example.browserworkerclient.dto.FetchResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class EmagProductParser implements ProductParser {

    private static final String TITLE_SELECTOR =
            "h1.page-title";

    private static final String PRICE_SELECTOR =
            ".product-new-price";

    @Override
    public ProductScrapeResponse parse(FetchResult result) {

        Document doc = Jsoup.parse(result.html());

        String title = doc.select(TITLE_SELECTOR).text();

        String rawPrice = doc.select(PRICE_SELECTOR).first().text();


        return new ProductScrapeResponse(
                result.url(),
                title,
                rawPrice,
                "RON",
                true,
                null,
                null,
                "emag",
                Instant.now()
        );
    }
}