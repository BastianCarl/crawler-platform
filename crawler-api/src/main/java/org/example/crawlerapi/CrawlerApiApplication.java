package org.example.crawlerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "org.example.crawlerapi",
        "org.example.crawlerfetcher",
        "org.example.crawlerparser",
})
public class CrawlerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawlerApiApplication.class, args);
    }

}