package org.example.crawlercore;

import org.springframework.stereotype.Component;

@Component
public class EmagUrlNormalizer implements UrlNormalizer {

    @Override
    public String normalize(String url) {
        int queryIndex = url.indexOf("?");
        if (queryIndex != -1) {
            url = url.substring(0, queryIndex);
        }
        int hashIndex = url.indexOf("#");
        if (hashIndex != -1) {
            url = url.substring(0, hashIndex);
        }
        return url;
    }
}
