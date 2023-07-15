CREATE TABLE portfolio(
                          id text PRIMARY KEY,
                          name text NOT NULL UNIQUE,
                          description text,
                          created_at timestamp,
                          modified_at timestamp,
                          version int
);