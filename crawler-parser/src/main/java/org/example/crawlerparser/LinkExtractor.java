package org.example.crawlerparser;

import java.util.Set;
import org.example.browserworkerclient.dto.FetchResult;
import org.springframework.stereotype.Service;

@Service
public interface LinkExtractor {

    Set<String> extract(FetchResult result);
}
