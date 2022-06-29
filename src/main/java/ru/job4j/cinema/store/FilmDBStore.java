package ru.job4j.cinema.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import ru.job4j.cinema.model.Film;

@ThreadSafe
@Repository
public class FilmDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(FilmDBStore.class.getName());
    private final BasicDataSource pool;

    public FilmDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Collection<Film> findAll() {
        Collection<Film> films = new ArrayList<>();
        try (Connection cn = pool.getConnection();
            PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM films")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Film film = new Film(
                            it.getInt("id"),
                            it.getString("name"));
                    films.add(film);
                }
            }
        } catch (SQLException e) {
            LOG.error("Exception in FilmDBStore#findAll()", e);
        }
        return films;
    }

    public Optional<Film> findById(int id) {
        Film film = new Film();
        Optional<Film> result = Optional.empty();
        try (Connection cn = pool.getConnection();
            PreparedStatement ps = cn.prepareStatement(
                    "SELECT * FROM films WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    film.setFilmId(it.getInt("id"));
                    film.setFilmName(it.getString("name"));
                    result = Optional.of(film);
                }
            }
        } catch (SQLException e) {
            LOG.error("Exception in FilmDBStore#findById(int id)", e);
        }
        return result;
    }

    public Optional<Film> add(Film film) {
        Optional<Film> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO films (name) VALUES (?);",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, film.getFilmName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    film.setFilmId(id.getInt(1));
                    result = Optional.of(film);
                }
            }
        } catch (SQLException e) {
            LOG.error("Exception in FilmDBStore#add(Film film)", e);
        }
        return result;
    }

}
