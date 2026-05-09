
---

# `crawler-worker/README.md`

```md
# crawler-worker

Executes the actual crawling tasks.

## Responsibilities

- consume tasks from queue
- fetch pages
- parse pages
- extract links
- persist results
- handle local retries

## Workflow

```text
poll task
    ↓
fetch page
    ↓
parse content
    ↓
extract links
    ↓
save results
    ↓
enqueue discovered links