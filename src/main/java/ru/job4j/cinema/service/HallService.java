package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.TicketDBStore;

import java.util.Collection;

@ThreadSafe
@Service
public class HallService {
    private static final int ROW = 4;
    private static final int CELL = 5;
    private final TicketDBStore store;

    public HallService(TicketDBStore store) {
        this.store = store;
    }

    public int[][] getHall(int filmId) {
        int[][] result = new int[ROW][CELL];
        if (filmId != 0) {
            int row;
            int cell;
            Collection<Ticket> filmsTickets = store.getAllTicketForFilm(filmId);
            for (Ticket ticket : filmsTickets) {
                row = ticket.getRow();
                cell = ticket.getCell();
                result[row - 1][cell - 1] = 1;
            }
        }
        return result;
    }
}


