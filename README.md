# java-shareit
Template repository for Shareit project.

## Entity Relationship

```mermaid
erDiagram
    USERS ||--o{ ITEM: has
    USERS ||--|{ BOOKING: requests
    USERS ||--o{ REQUEST: issues
    BOOKING ||--|{ ITEM: books
    USERS ||--o{ COMMENT: creates
    ITEM ||--o{ COMMENT: has
    ITEM }o--o{ REQUEST_ITEM: relates
    REQUEST }|--o{ REQUEST_ITEM: relates
    USERS {
        bigint id PK
        string name
        string email
    }
    ITEM {
        bigint id PK
        string name
        string description
        bigint ownerId FK
        boolean available
    }
    BOOKING {
        bigint id PK
        timestamp start
        timestamp end
        bigint item_id FK
        bigint booker_id FK
        string status
    }
    COMMENT {
        bigint id PK
        string text
        bigint item_id FK
        bigint author_id FK
        timestamp created
    }
    REQUEST {
        bigint id PK
        string description
        timestamp created
        bigint requestor_id FK
    }
    REQUEST_ITEM {
        bigint id PK
        bigint request_id FK
        bigint item_id FK
    }
```