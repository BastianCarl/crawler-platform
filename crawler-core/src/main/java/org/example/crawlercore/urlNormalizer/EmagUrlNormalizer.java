package org.example.crawlercore.urlNormalizer;

import java.net.URI;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmagUrlNormalizer implements UrlNormalizer {
    private static final Logger log = LoggerFactory.getLogger(EmagUrlNormalizer.class);

    @Override
    public Optional<String> normalize(String rawHref, String parentUrl) {
        try {
            String sanitizedHref = sanitizeHref(rawHref);
            String resolvedUrl = resolveUrl(sanitizedHref, parentUrl);
            String normalizedUrl = normalizeResolvedUrl(resolvedUrl);

            return Optional.of(normalizedUrl);
        } catch (Exception e) {
            log.warn("Failed to normalize URL: {} with parent URL: {}. Error: {}", rawHref, parentUrl, e.getMessage());
            return Optional.empty();
        }
    }

    public Set<String> normalize(Set<String> hrefs, String parentUrl) {
        return hrefs.stream()
                .map(href -> normalize(href, parentUrl))
                .flatMap(Optional::stream)
                .collect(java.util.stream.Collectors.toSet());
    }

    private String sanitizeHref(String href) {
        href = removeQuery(href);
        href = removeFragment(href);
        return lowercase(href);
    }

    private String resolveUrl(String href, String parentUrl) {
        return URI.create(parentUrl).resolve(href).toString();
    }

    private String normalizeResolvedUrl(String url) {
        return removeTrailingSlash(url);
    }

    private String removeQuery(String url) {
        int queryIndex = url.indexOf('?');
        return queryIndex != -1 ? url.substring(0, queryIndex) : url;
    }

    private String removeFragment(String url) {
        int hashIndex = url.indexOf('#');
        return hashIndex != -1 ? url.substring(0, hashIndex) : url;
    }

    private String lowercase(String href) {
        return href.toLowerCase();
    }

    private String removeTrailingSlash(String href) {
        if (href.endsWith("/")) {
            return href.substring(0, href.length() - 1);
        }
        return href;
    }
}
