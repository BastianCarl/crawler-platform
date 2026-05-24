package org.example.crawlercore.validator;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import org.example.crawlercore.model.CrawlJob;
import org.example.crawlercore.urlDeduplicator.InMemoryUrlDeduplicator;
import org.example.crawlercore.urlFilter.EmagUrlFilter;
import org.springframework.stereotype.Component;

@Component
public class CrawlJobValidator {

    private final EmagUrlFilter urlFilter;
    private final InMemoryUrlDeduplicator deduplicator;

    public CrawlJobValidator(EmagUrlFilter urlFilter, InMemoryUrlDeduplicator deduplicator) {
        this.urlFilter = urlFilter;
        this.deduplicator = deduplicator;
    }

    public boolean isValid(String url, int currentDepth, int maxDepth) {

        if (url == null || url.isBlank()) {
            return false;
        }

        if (currentDepth > maxDepth) {
            return false;
        }

        if (!urlFilter.shouldVisit(url)) {
            return false;
        }

        return deduplicator.shouldVisit(url);
    }

    public boolean isSameDomainAsParent(String parentUrl, String url) {
        try {
            URI rootUri = URI.create(parentUrl);
            URI currentUri = URI.create(url);
            String rootHost = rootUri.getHost();
            String currentHost = currentUri.getHost();
            if (rootHost == null || currentHost == null) {
                return false;
            }
            return rootHost.equalsIgnoreCase(currentHost);
        } catch (Exception e) {

            return false;
        }
    }

    public Set<String> collectCrawlableUrls(CrawlJob parentJob, int maxDepth, Set<String> links) {
        Set<String> filtered = new HashSet<>();
        for (String link : links) {
            if (isValid(link, parentJob.depth() + 1, maxDepth) && isSameDomainAsParent(parentJob.url(), link)) {
                filtered.add(link);
            }
        }
        return filtered;
    }
}
