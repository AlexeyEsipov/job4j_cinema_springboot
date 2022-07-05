package ru.job4j.cinema.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.HallService;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HallControllerTest {

    @Test
    public void whenGetHall() {
        Film film = new Film();
        User user = new User();
        int[][] hall = new int[2][];
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        FilmService filmService = mock(FilmService.class);
        HallService hallService = mock(HallService.class);
        HallController hallController = new HallController(filmService, hallService);
        when(session.getAttribute("user")).thenReturn(user);
        when(hallService.getHall(film.getFilmId())).thenReturn(hall);
        when(filmService.setFilmName(film)).thenReturn(film);
        String page = hallController.getHall(model, film, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("m", hall);
        verify(model).addAttribute("film", film);
        assertEquals("hall", page);
    }

}