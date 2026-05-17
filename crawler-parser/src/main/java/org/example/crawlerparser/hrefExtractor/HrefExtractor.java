package org.example.crawlerparser.hrefExtractor;

import java.util.Set;
import org.example.browserworkerclient.dto.FetchResult;
import org.springframework.stereotype.Service;

@Service
public interface HrefExtractor {

    Set<String> extract(FetchResult result);
}
