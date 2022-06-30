package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.TicketDBStore;

import java.util.Collection;

@ThreadSafe
@Service
public class HallService {
    private static final int ROW = 3;
    private static final int CELL = 3;
    private final TicketDBStore store;
    private int[][][] hall;

    public HallService(TicketDBStore store) {
        this.store = store;
        initHall();
    }

    public int[][][] getHall(int filmId) {
        int[][][] result = hall;
        if (filmId != 0) {
            int row;
            int cell;
            Collection<Ticket> filmsTickets = store.getAllTicketForFilm(filmId);
            for (Ticket ticket : filmsTickets) {
                row = ticket.getRow();
                cell = ticket.getCell();
                result[row - 1][cell - 1][0] = row;
                result[row - 1][cell - 1][1] = 0;
            }
        }
        return result;
    }

    /** Формируем заготовку зала - трехмерный массив.
       Первое размерение - индекс ряда, начиная с 0.
       Второе размерение - индекс места, начиная с 0.
       Третье размерение - массив из двух элементов, в котором
       элемент с индексом 0 - это номер ряда начиная с 1,
       элемент с индексом 1 - это номер места, начиная с 1   */
    private void initHall() {
        this.hall = new int[ROW][CELL][2];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                hall[i][j][0] = i + 1;
                hall[i][j][1] = j + 1;
            }
        }
    }
}


