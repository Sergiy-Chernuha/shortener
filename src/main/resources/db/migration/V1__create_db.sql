CREATE TABLE IF NOT EXISTS USERS (
    id bigint generated always as identity PRIMARY KEY,
    nick_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(1024) NOT NULL

);

CREATE TABLE IF NOT EXISTS URL (
    short_url VARCHAR(200) PRIMARY KEY,
    long_url VARCHAR(1024) NOT NULL,
    create_date TIMESTAMP(6) NOT NULL,
    expiry_date TIMESTAMP(6) NOT NULL,
    clicks_count INTEGER NOT NULL,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE
);
