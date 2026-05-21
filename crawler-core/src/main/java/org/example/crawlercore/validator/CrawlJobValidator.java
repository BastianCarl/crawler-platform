package org.example.crawlercore.validator;

import org.example.crawlercore.model.CrawlJob;
import org.example.crawlercore.urlDeduplicator.InMemoryUrlDeduplicator;
import org.example.crawlercore.urlFilter.EmagUrlFilter;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@Component
public class CrawlJobValidator {

    private final EmagUrlFilter urlFilter;
    private final InMemoryUrlDeduplicator deduplicator;

    public CrawlJobValidator(
            EmagUrlFilter urlFilter,
            InMemoryUrlDeduplicator deduplicator) {
        this.urlFilter = urlFilter;
        this.deduplicator = deduplicator;
    }

    public boolean isValid(CrawlJob job, int maxDepth) {

        if (job == null) {
            return false;
        }

        String url = job.url();

        if (url == null || url.isBlank()) {
            return false;
        }

        if (job.depth() > maxDepth) {
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
            if (isValid(new CrawlJob(link, parentJob.depth() + 1), maxDepth) && isSameDomainAsParent(link, parentJob.url())) {
                filtered.add(link);
            }
        }
        return filtered;
    }
}