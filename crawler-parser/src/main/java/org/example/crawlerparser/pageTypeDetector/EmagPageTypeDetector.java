package org.example.crawlerparser.pageTypeDetector;

import org.example.browserworkerclient.dto.FetchResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class EmagPageTypeDetector implements PageTypeDetector {

    @Override
    public PageType getType(FetchResult result) {

        if (isProduct(result)) {
            return PageType.PRODUCT;
        } else return PageType.NON_PRODUCT;
    }

    private boolean isProduct(FetchResult result) {
        int score = 0;

        if (result.url().contains("/pd/")) {
            score += 30;
        }

        if (hasJsonLdProduct(result)) {
            score += 50;
        }

        if (score >= 30) {
            return true;
        }
        return false;
    }

    private boolean hasJsonLdProduct(FetchResult result) {
        Document doc = Jsoup.parse(result.html());
        Elements scripts = doc.select("script[type=application/ld+json]");
        for (Element script : scripts) {
            String json = script.html();
            if (json.contains("\"@type\":\"Product\"")) {
                return true;
            }
        }
        return false;
    }
}
