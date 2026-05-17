package org.example.crawlerparser;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.browserworkerclient.dto.FetchResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class EmagLinkExtractor implements LinkExtractor {
    private final Set<String> BLOCKED_PATHS = Set.of(
            "/filter",
            "/vendor",
            "/rating",
            "/help",
            "/info",
            "/user",
            "/history"
    );
    @Override
    public Set<String> extract(FetchResult result) {

        Document doc = Jsoup.parse(result.html());

        return doc.select("a[href]").stream()
                .map(element -> element.absUrl("href"))
                .filter(url -> url.startsWith("https://www.emag.ro"))
                .filter(this::isAllowedUrl)
                .collect(Collectors.toSet());
    }

    private boolean isAllowedUrl(String url) {
        return BLOCKED_PATHS.stream()
                .noneMatch(url::contains);
    }
}
