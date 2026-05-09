
---

# `crawler-fetcher/README.md`

```md
# crawler-fetcher

Responsible for retrieving web page content.

## Responsibilities

- HTTP requests
- browser automation
- session handling
- cookies
- headers
- proxy management
- throttling
- anti-bot handling

## Fetcher Types

### Static pages

- Jsoup
- HttpClient

### Dynamic pages

- Playwright
- browser contexts
- browser pooling

## Recommended Structure

```text
fetcher/
 ├── http/
 ├── browser/
 ├── proxy/
 ├── session/
 ├── stealth/
 └── throttling/