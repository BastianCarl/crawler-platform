package org.example.crawlerfetcher;

import org.example.browserworkerclient.BrowserWorkerClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("browser.worker.url")
    private String baseUrl;

    @Bean
    public RestClient browserRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public BrowserWorkerClient browserWorkerClient(RestClient browserRestClient) {
        return new BrowserWorkerClient(browserRestClient);
    }
}