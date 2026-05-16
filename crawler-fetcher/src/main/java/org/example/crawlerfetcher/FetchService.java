package org.example.crawlerfetcher;

import org.example.browserworkerclient.dto.FetchResult;
import org.example.browserworkerclient.dto.FetcherRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Component
public class FetchService {

    private  RestClient restClient;
    @Autowired
    public FetchService(RestClient restClient) {
        this.restClient = restClient;
    }

    public FetchResult fetch(FetcherRequest fetcherRequest) {
        return restClient
                .post()
                .uri("/fetch")
                .body(fetcherRequest)
                .retrieve()
                .body(FetchResult.class);
    }
}
