package org.example.crawlerparser.hrefExtractor;

import java.util.Set;
import java.util.stream.Collectors;
import org.example.browserworkerclient.dto.FetchResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class EmagHrefExtractor implements HrefExtractor {
    private final Set<String> BLOCKED_PATHS = Set.of(
            "/filter",
            "/vendor",
            "/rating",
            "/help",
            "/info",
            "/user",
            "/history",
            "/brand/",
            "/pret,",
            "/stoc",
            "/noutati",
            "/resigilate");

    @Override
    public Set<String> extract(FetchResult result) {

        Document doc = Jsoup.parse(result.html());

        return doc.select("a[href]").stream()
                .map(element -> element.attr("href"))
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
