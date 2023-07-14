CREATE TABLE portfolio(
                          id text PRIMARY KEY,
                          name text NOT NULL UNIQUE,
                          description text,
                          created_at timestamp,
                          modified_at timestamp,
                          version int
);

INSERT INTO portfolio (id, name, description, version) values ('61165bad-3e70-475a-ac52-b193b66d77f5', 'asia', 'stocks from asia', 1);
INSERT INTO portfolio (id, name, description, version) values ('61165bad-3e70-475a-ac52-b193b66d77f4', 'usa', 'stocks from usa', 1)