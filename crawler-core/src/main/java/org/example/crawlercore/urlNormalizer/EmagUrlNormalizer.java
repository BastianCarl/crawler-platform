package org.example.crawlercore.urlNormalizer;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class EmagUrlNormalizer implements UrlNormalizer {
    private static final Path VISITED_URLS_FILE = Paths.get("visited-urls2.txt");

    @Override
    public String normalize(String parentUrl, String rawHref) {
        try {
            int queryIndex = rawHref.indexOf("?");
            if (queryIndex != -1) {
                rawHref = rawHref.substring(0, queryIndex);
            }
            int hashIndex = rawHref.indexOf("#");
            if (hashIndex != -1) {
                rawHref = rawHref.substring(0, hashIndex);
            }
            rawHref = lowercase(rawHref);
            String result = String.valueOf(URI.create(parentUrl).resolve(rawHref));
            return removeTrailingSlash(result);
        } catch (Exception e) {
                try {
                    Files.writeString(
                            VISITED_URLS_FILE,
                            rawHref + System.lineSeparator(),
                            StandardOpenOption.CREATE,
                            StandardOpenOption.APPEND);
                } catch (IOException ex) {
                    throw new RuntimeException("Failed to write to visited URLs file", ex);
                }
        }
        return null;
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
