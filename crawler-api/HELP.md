# crawler-api

Main REST API entry point for the crawling platform.

## Responsibilities

- expose REST endpoints
- manage crawl jobs
- monitor workers
- provide crawling status
- manage retries
- backend for admin dashboard

## Should NOT contain

- HTML parsing logic
- fetching logic
- Playwright/browser logic
- complex business orchestration

## Example Endpoints

### Crawl jobs

```http
POST /api/crawls
GET /api/crawls
GET /api/crawls/{id}
DELETE /api/crawls/{id}