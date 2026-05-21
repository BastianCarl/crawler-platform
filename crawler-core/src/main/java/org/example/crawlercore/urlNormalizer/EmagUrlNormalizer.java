package org.example.crawlercore.urlNormalizer;

import java.net.URI;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class EmagUrlNormalizer implements UrlNormalizer {

    @Override
    public String normalize(String parentUrl, String rawHref) {
        int queryIndex = rawHref.indexOf("?");
        if (queryIndex != -1) {
            rawHref = rawHref.substring(0, queryIndex);
        }
        int hashIndex = rawHref.indexOf("#");
        if (hashIndex != -1) {
            rawHref = rawHref.substring(0, hashIndex);
        }
        rawHref = lowercase(rawHref);
        rawHref = removeTrailingSlash(rawHref);
        return String.valueOf(URI.create(parentUrl).resolve(rawHref));
    }

    private String lowercase(String url) {
        return url.toLowerCase();
    }

    private String removeTrailingSlash(String url) {
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

    public Set<String> normalize(Set<String> urls, String parentUrl) {
        return urls.stream()
                .map(url -> normalize(parentUrl, url))
                .collect(java.util.stream.Collectors.toSet());
    }
}
