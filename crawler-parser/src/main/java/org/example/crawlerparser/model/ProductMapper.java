package org.example.crawlerparser.model;

import org.springframework.stereotype.Component;

@Component
public final class ProductMapper {

    public ProductEntity toEntity(ProductScrapeResponse response) {
        ProductEntity entity = new ProductEntity();
        entity.setUrl(response.url());
        entity.setTitle(response.title());
        entity.setCurrentPrice(response.price());
        entity.setCurrency(response.currency());
        entity.setCurrentInStock(response.inStock());
        entity.setSource(response.source());
        entity.setFirstSeenAt(response.scrapedAt());
        entity.setLastSeenAt(response.scrapedAt());
        return entity;
    }
}