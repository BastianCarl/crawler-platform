package org.example.crawlerparser.respository;

import java.util.Optional;
import org.example.crawlerparser.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByUrl(String url);

    boolean existsByUrl(String url);
}
