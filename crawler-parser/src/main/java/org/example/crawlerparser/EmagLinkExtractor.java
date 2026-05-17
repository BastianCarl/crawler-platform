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
                .filter(this::isAllowedUrl)
                .collect(Collectors.toSet());
    }

    private boolean isAllowedUrl(String href) {
        return BLOCKED_PATHS.stream().noneMatch(href::contains) && isValidHref(href);
    }

    private boolean isValidHref(String href) {
        return href != null
                && !href.isBlank()
                && !href.startsWith("#")
                && !href.startsWith("javascript:")
                && !href.startsWith("mailto:")
                && !href.startsWith("tel:");
    }
}
