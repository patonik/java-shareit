# java-shareit
Template repository for Shareit project.

## Entity Relationship

```mermaid
erDiagram
    USERS ||--o{ ITEM: has
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
```