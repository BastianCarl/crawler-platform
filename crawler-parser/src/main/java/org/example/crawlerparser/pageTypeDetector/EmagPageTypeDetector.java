package org.example.crawlerparser.pageTypeDetector;
import org.springframework.stereotype.Component;

@Component
public class EmagPageTypeDetector implements PageTypeDetector {

    @Override
    public PageType getType(String url) {
        if (isProduct(url)) {
            return PageType.PRODUCT;
        } else return PageType.NON_PRODUCT;
    }

    private boolean isProduct(String url) {
        return url.contains("/pd/");
    }
}
