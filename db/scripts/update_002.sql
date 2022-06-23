DROP TABLE IF EXISTS films CASCADE ;
CREATE TABLE IF NOT EXISTS films (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(250)
);