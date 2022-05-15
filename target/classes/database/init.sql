CREATE TABLE IF NOT EXISTS users
(
    id SERIAL PRIMARY KEY,
    username text not null,
    password text not null
);

CREATE TABLE IF NOT EXISTS chats
(
    id SERIAL PRIMARY KEY,
    name text not null,
    description text,
    creator TEXT NOT NULL
);