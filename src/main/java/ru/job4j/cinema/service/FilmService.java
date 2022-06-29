package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.store.FilmDBStore;

import java.util.Collection;

@ThreadSafe
@Service
public class FilmService {
    private final FilmDBStore store;

    public FilmService(FilmDBStore store) {
        this.store = store;
    }

    public Collection<Film> findAll() {
        return store.findAll();
    }

    public Film findById(int id) {
        return store.findById(id).orElseThrow();
    }

    public Film setFilmName(Film film) {
        return store.findById(film.getFilmId()).orElseThrow();
    }
}
