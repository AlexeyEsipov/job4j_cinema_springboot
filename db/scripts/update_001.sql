DROP TABLE IF EXISTS users CASCADE ;
CREATE TABLE IF NOT EXISTS users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       email VARCHAR(50) NOT NULL UNIQUE,
                       phone VARCHAR(30) NOT NULL UNIQUE
);