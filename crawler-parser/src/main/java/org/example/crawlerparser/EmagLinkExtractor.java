package org.example.crawlerparser;

import org.example.browserworkerclient.dto.FetchResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmagLinkExtractor implements LinkExtractor {

    @Override
    public Set<String> extract(FetchResult result) {

        Document doc = Jsoup.parse(result.html());

        return doc.select("a[href]")
                .stream()
                .map(element -> element.absUrl("href"))
                .filter(url -> url.contains("/pd/"))
                .collect(Collectors.toSet());
    }
}