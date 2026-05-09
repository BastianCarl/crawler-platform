
---

# `crawler-storage/README.md`

```md
# crawler-storage

Persistence layer of the crawler platform.

## Responsibilities

- entities
- repositories
- crawl history
- snapshots
- persistence
- database migrations

## Example Entities

- CrawlJob
- CrawlTask
- CrawlResult
- CrawlSnapshot
- FailedRequest

## Recommended Structure

```text
storage/
 ├── entity/
 ├── repository/
 ├── migration/
 ├── config/
 └── snapshot/