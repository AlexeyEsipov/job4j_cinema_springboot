package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import java.util.Optional;

import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.TicketDBStore;

@ThreadSafe
@Service
public class TicketService {
    private final TicketDBStore store;

    public TicketService(TicketDBStore store) {
        this.store = store;
    }

    public Optional<Ticket> addTicket(Ticket ticket) {
        return store.addTicket(setTicket(ticket));
    }

    public Ticket setTicket(Ticket ticket) {
        String cell = ticket.getGeneralCell();
        int filmId = Integer.parseInt(cell.substring(cell.indexOf("F") + 1, cell.indexOf("U")));
        int userId = Integer.parseInt(cell.substring(cell.indexOf("U") + 1));
        int row = Integer.parseInt(cell.substring(0, cell.indexOf("C")));
        int col = Integer.parseInt(cell.substring(cell.indexOf("C") + 1, cell.indexOf("F")));
        return new Ticket(filmId, userId, row, col);
    }
}
