package org.example.crawlercore;

public interface UrlNormalizer {

    String normalize(String baseUrl, String rawHref);
}
