package org.example.crawlerparser;

import org.example.browserworkerclient.dto.FetchResult;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface LinkExtractor {

    Set<String> extract(FetchResult result);

}