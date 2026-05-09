
---

# `crawler-strategies/README.md`

```md
# crawler-strategies

Contains site-specific crawling strategies.

## Responsibilities

- custom parsing per site
- custom selectors
- anti-bot workarounds
- extraction rules
- domain-specific crawl policies

## Examples

- emag strategy
- amazon strategy
- booking strategy
- generic strategy

## Recommended Structure

```text
strategies/
 ├── emag/
 ├── amazon/
 ├── booking/
 ├── olx/
 └── generic/