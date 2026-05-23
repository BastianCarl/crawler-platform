package org.example.crawlerparser.respository;

import org.example.crawlerparser.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByUrl(String url);
    boolean existsByUrl(String url);
}