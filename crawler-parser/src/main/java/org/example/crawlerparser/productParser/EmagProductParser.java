package org.example.crawlerparser.productParser;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import org.example.browserworkerclient.dto.FetchResult;
import org.example.crawlerparser.model.NonProductScrapeResponse;
import org.example.crawlerparser.model.ProductScrapeResponse;
import org.example.crawlerparser.model.ScrapeResponse;
import org.example.crawlerparser.pageTypeDetector.PageType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmagProductParser implements ProductParser {
    private static final String PRICE_SELECTOR = ".product-new-price";
    private static final Logger log = LoggerFactory.getLogger(EmagProductParser.class);

    @Override
    public Optional<ScrapeResponse> parse(PageType pageType, FetchResult result) {
        try {

            Document doc = Jsoup.parse(result.html());
            String title = extractTitle(doc);
            if (pageType == PageType.PRODUCT) {
                String price = extractPrice(doc);
                boolean isInStock = extractInStock(doc);
                return Optional.of(new ProductScrapeResponse(
                        result.url(), title, new BigDecimal(price), "RON", isInStock, "emag", Instant.now()));
            } else {
                return Optional.of(new NonProductScrapeResponse(result.url(), title, "emag", Instant.now()));
            }
        } catch (Exception e) {
            log.warn("Failed to parse product page: {}, error: {}", result.url(), e.getMessage());
            return Optional.empty();
        }
    }

    private String extractPrice(Document document) {
        Element priceElement = document.selectFirst(PRICE_SELECTOR);
        if (priceElement == null) {
            return null;
        }
        String whole = priceElement.ownText().replace(".", "").replace(",", "").trim();
        Element supElement = priceElement.selectFirst("sup");
        String decimal = "00";
        if (supElement != null) {
            decimal = supElement.text().replace(",", "").replace(".", "").trim();
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
        title = title.replace(" - eMAG.ro", "").trim();
        return title;
    }
}
