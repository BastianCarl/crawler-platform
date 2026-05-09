
---

# `crawler-core/README.md`

```md
# crawler-core

Contains the central orchestration logic of the crawler platform.

## Responsibilities

- crawl lifecycle management
- scheduling
- retry logic
- rate limiting
- URL frontier management
- deduplication
- worker orchestration
- crawl coordination

## Example Components

- CrawlScheduler
- FrontierManager
- RetryManager
- CrawlCoordinator
- DomainRateLimiter

## Should NOT contain

- HTML parsing
- Playwright/browser code
- database repositories
- REST controllers

## Recommended Structure

```text
core/
 ├── scheduler/
 ├── frontier/
 ├── dedup/
 ├── retry/
 ├── throttling/
 ├── orchestration/
 └── lifecycle/