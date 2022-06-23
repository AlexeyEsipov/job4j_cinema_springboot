DROP TABLE IF EXISTS ticket CASCADE ;
CREATE TABLE IF NOT EXISTS ticket (
                                      id SERIAL PRIMARY KEY,
                                      film_id int NOT NULL REFERENCES films(id),
                                      pos_row int NOT NULL,
                                      cell int NOT NULL,
                                      user_id int NOT NULL REFERENCES users(id)
);
ALTER TABLE ticket ADD CONSTRAINT film_row_cell_unique UNIQUE (film_id, pos_row, cell);
