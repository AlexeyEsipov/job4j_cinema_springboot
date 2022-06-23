package ru.job4j.cinema.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmService;

public class IndexControllerTest {

    @Test
    public void whenIndex() {
        Film filmOne = new Film();
        Film filmTwo = new Film();
        Collection<Film> films = List.of(filmOne, filmTwo);
        User user = new User();
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        FilmService filmService = mock(FilmService.class);
        IndexController indexController = new IndexController(filmService);
        when(session.getAttribute("user")).thenReturn(user);
        when(filmService.findAll()).thenReturn(films);
        String page = indexController.index(model, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("films", films);
        assertEquals("index", page);
    }
}