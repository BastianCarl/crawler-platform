package org.example.crawlerparser.productParser;

import java.net.URI;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.example.browserworkerclient.dto.FetchResult;
import org.example.crawlerparser.model.ProductScrapeResponse;
import org.example.crawlerparser.pageTypeDetector.PageType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class EmagProductParser implements ProductParser {
    private static final String PRICE_SELECTOR = ".product-new-price";

    @Override
    public ProductScrapeResponse parse(PageType pageType, FetchResult result) {
        Document doc = Jsoup.parse(result.html());
        String title = extractTitle(doc);
        if (pageType == PageType.PRODUCT) {
            String price = extractPrice(doc);
            boolean isInStock = extractInStock(doc);
            return new ProductScrapeResponse(result.url(), title, price, "RON", isInStock, "emag", Instant.now());
        } else {
            return new ProductScrapeResponse(result.url(), title, null, null, true, "emag", Instant.now());
        }
    }

    private String extractPrice(Document document) {
        Element priceElement = document.selectFirst(PRICE_SELECTOR);
        if (priceElement == null) {
            return null;
        }
        String whole = priceElement.ownText()
                .replace(".", "")
                .replace(",", "")
                .trim();
        Element supElement = priceElement.selectFirst("sup");
        String decimal = "00";
        if (supElement != null) {
            decimal = supElement.text()
                    .replace(",", "")
                    .replace(".", "")
                    .trim();
        }
        return whole + "." + decimal;
    }

    private boolean extractInStock(Document document) {
        Element stockElement = document.selectFirst(".label.label-in_stock");
        if (stockElement == null) {
            return false;
        }
        String stockText = stockElement.text().trim().toLowerCase();
        return stockText.contains("în stoc") || stockText.contains("in stoc");
    }

    private String extractTitle(Document document) {
        String title = document.title();
        if (title == null || title.isBlank()) {
            return null;
        }
        title = title.replace(" - eMAG.ro", "")
                .trim();
        return title;
    }
}
