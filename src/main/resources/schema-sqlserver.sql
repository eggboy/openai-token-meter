CREATE TABLE token_count_usage
(
--     id                UNIQUEIDENTIFIER NOT NULL PRIMARY KEY DEFAULT NEWID(),
    id CHAR(36) NOT NULL PRIMARY KEY,
    product_id        VARCHAR(255)     NOT NULL,
    endpoint          VARCHAR(255)     NOT NULL,
    model             VARCHAR(255)     NOT NULL,
    event_time        DATETIME         NOT NULL,
    prompt_tokens     INT,
    completion_tokens INT,
    total_tokens      INT
);