package org.example.crawlercore.urlNormalizer;

import java.util.Optional;

public interface UrlNormalizer {

    Optional<String> normalize(String baseUrl, String rawHref);
}
