package ru.job4j.cinema.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TicketControllerTest {
    private final static String FAIL = "Это место уже купили, выберите другое";

    @Test
    public void whenCreateTicket() {
        Film film = new Film();
        int[][][] hall = new int[3][][];
        User user = new User();
        Ticket ticket = new Ticket();
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        FilmService filmService = mock(FilmService.class);
        HallService hallService = mock(HallService.class);
        TicketService ticketService = mock(TicketService.class);
        TicketController ticketController =
                new TicketController(ticketService, filmService, hallService);
        when(ticketService.addTicket(ticket)).thenReturn(Optional.of(ticket));
        when(session.getAttribute("user")).thenReturn(user);
        when(hallService.getHall(ticket.getTicketFilmId())).thenReturn(hall);
        when(filmService.findById(ticket.getTicketFilmId())).thenReturn(film);
        String page = ticketController.createTicket(model, ticket, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("m", hall);
        verify(model).addAttribute("film", film);
        assertEquals("hall", page);
    }

    @Test
    public void whenNotCreateTicket() {
        User user = new User();
        Ticket ticket = new Ticket();
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        FilmService filmService = mock(FilmService.class);
        HallService hallService = mock(HallService.class);
        TicketService ticketService = mock(TicketService.class);
        TicketController ticketController =
                new TicketController(ticketService, filmService, hallService);
        when(ticketService.addTicket(ticket)).thenReturn(Optional.empty());
        when(session.getAttribute("user")).thenReturn(user);
        String page = ticketController.createTicket(model, ticket, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("message", FAIL);
        assertEquals("notconfirm", page);
    }

    @Test
    public void whenConfirmBuyTicket() {
        Film film = new Film();
        User user = new User();
        Ticket ticket = new Ticket();
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        FilmService filmService = mock(FilmService.class);
        HallService hallService = mock(HallService.class);
        TicketService ticketService = mock(TicketService.class);
        TicketController ticketController =
                new TicketController(ticketService, filmService, hallService);
        when(ticketService.setTicket(ticket)).thenReturn(ticket);
        when(session.getAttribute("user")).thenReturn(user);
        when(filmService.findById(ticket.getTicketFilmId())).thenReturn(film);
        String page = ticketController.confirmBuyTicket(model, ticket, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("ticket", ticket);
        verify(model).addAttribute("film", film);
        assertEquals("buyticket", page);
    }
}