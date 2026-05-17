package org.example.crawlercore.urlFilter;

import org.springframework.stereotype.Component;

@Component
public class EmagUrlFilter implements UrlFilter {

    @Override
    public boolean shouldVisit(String url) {

        //        return !url.contains("sponsored_products")
        //                && !url.contains("embedding_similar_model")
        //                && !url.contains("provider=rec")
        //                && !url.contains("scenario_ID")
        //                && !url.contains("recid")
        //                && !url.contains("?ref=fam");

        return true;
    }
}
