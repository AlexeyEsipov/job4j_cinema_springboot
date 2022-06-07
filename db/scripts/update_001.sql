DROP TABLE IF EXISTS users CASCADE ;
DROP TABLE IF EXISTS sessions CASCADE ;
DROP TABLE IF EXISTS ticket CASCADE ;
CREATE TABLE IF NOT EXISTS users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       email VARCHAR(50) NOT NULL UNIQUE,
                       phone VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS sessions (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(250)
);

CREATE TABLE IF NOT EXISTS ticket (
                        id SERIAL PRIMARY KEY,
                        session_id INT NOT NULL REFERENCES sessions(id),
                        pos_row INT NOT NULL,
                        cell INT NOT NULL,
                        user_id INT NOT NULL REFERENCES users(id),
                        UNIQUE(session_id, pos_row, cell)
);