package ru.job4j.cinema.store;

import org.junit.After;
import org.junit.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;

public class TicketDBStoreTest {
    private static final UserDBStore USER_DB_STORE = new UserDBStore(new Main().loadPool());
    private static final FilmDBStore FILM_DB_STORE = new FilmDBStore(new Main().loadPool());
    private static final TicketDBStore TICKET_DB_STORE = new TicketDBStore(new Main().loadPool());
    private static final User USER = new User(0, "1", "1", "1");
    private static final Film FILM = new Film("1");

    @After
    public void clearStoreAfter() {
        TICKET_DB_STORE.deleteAllTicket();
        USER_DB_STORE.deleteAllUsers();
    }

    @Test
    public void whenAddTicket() {
        Optional<User> optionalUser = USER_DB_STORE.add(USER);
        Optional<Film> optionalFilm = FILM_DB_STORE.add(FILM);
        Ticket ticket = new Ticket(optionalFilm.orElseThrow().getFilmId(),
                optionalUser.orElseThrow().getUserId(), 3, 1);
        Optional<Ticket> result = TICKET_DB_STORE.addTicket(ticket);
        assertTrue(result.isPresent());
        assertSame(result.get().getTicketFilmId(), ticket.getTicketFilmId());
        assertSame(result.get().getCell(), ticket.getCell());
        assertSame(result.get().getRow(), ticket.getRow());
        assertEquals(result.get().getTicketUserId(), ticket.getTicketUserId());
    }

    @Test
    public void whenAdd2TicketsAndGet2Tickets() {
        Optional<User> optionalUser = USER_DB_STORE.add(USER);
        Optional<Film> optionalFilm = FILM_DB_STORE.add(FILM);
        Ticket ticketOne = new Ticket(optionalFilm.orElseThrow().getFilmId(),
                optionalUser.orElseThrow().getUserId(), 3, 1);
        Ticket ticketTwo = new Ticket(optionalFilm.get().getFilmId(),
                optionalUser.get().getUserId(), 1, 1);
        Optional<Ticket> t1 = TICKET_DB_STORE.addTicket(ticketOne);
        Optional<Ticket> t2 = TICKET_DB_STORE.addTicket(ticketTwo);
        assertTrue(t1.isPresent());
        assertTrue(t2.isPresent());
        Collection<Ticket> list =
                TICKET_DB_STORE.getAllTicketForFilm(optionalFilm.get().getFilmId());
        assertEquals(2, list.size());
    }
}