package org.example.crawlercore;

import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class EmagUrlNormalizer implements UrlNormalizer {

    @Override
    public String normalize(String baseUrl, String rawHref) {
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
        String resolved = String.valueOf(URI.create(baseUrl).resolve(rawHref));
        if (!isSameDomain(resolved)) {
            return null;
        }
        return resolved;
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


    private boolean isSameDomain(String url) {
        try {
            URI uri = URI.create(url);
            String host = uri.getHost();
            return host != null && host.equals("www.emag.ro");

        } catch (Exception e) {

            return false;
        }
    }


}
