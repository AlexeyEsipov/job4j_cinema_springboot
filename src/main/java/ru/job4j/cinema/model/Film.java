package ru.job4j.cinema.model;


import java.util.Objects;

public class Film {
    private int filmId;
    private String filmName;

    public Film(int filmId, String filmName) {
        this.filmId = filmId;
        this.filmName = filmName;
    }

    public Film(int filmId) {
        this.filmId = filmId;
    }

    public Film(String filmName) {
        this.filmName = filmName;
    }

    public Film() {
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Film)) {
            return false;
        }
        Film film = (Film) o;
        return filmId == film.filmId && Objects.equals(filmName, film.filmName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, filmName);
    }

    @Override
    public String toString() {
        return "Film{"
                + "filmId=" + filmId
                + ", filmName='" + filmName + '\''
                + '}';
    }
}
