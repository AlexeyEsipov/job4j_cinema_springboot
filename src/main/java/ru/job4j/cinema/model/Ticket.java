package ru.job4j.cinema.model;

import java.util.Objects;

public class Ticket {
    private int ticketId;
    private int ticketFilmId;
    private int ticketUserId;
    private int row;
    private int cell;
    private String generalCell;

    public Ticket(int ticketFilmId, int row, int cell) {
        this.ticketFilmId = ticketFilmId;
        this.row = row;
        this.cell = cell;
    }

    public Ticket() {
    }

    public Ticket(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    public Ticket(String generalCell) {
        this.generalCell = generalCell;
    }

    public Ticket(int ticketFilmId, int ticketUserId, int row, int cell) {
        this.ticketFilmId = ticketFilmId;
        this.ticketUserId = ticketUserId;
        this.row = row;
        this.cell = cell;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getTicketFilmId() {
        return ticketFilmId;
    }

    public void setTicketFilmId(int ticketFilmId) {
        this.ticketFilmId = ticketFilmId;
    }

    public int getTicketUserId() {
        return ticketUserId;
    }

    public void setTicketUserId(int ticketUserId) {
        this.ticketUserId = ticketUserId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public String getGeneralCell() {
        return generalCell;
    }

    public void setGeneralCell(String generalCell) {
        this.generalCell = generalCell;
    }

    @Override
    public String toString() {
        return "Ticket{"
                + "ticketId=" + ticketId
                + ", ticketFilmId=" + ticketFilmId
                + ", ticketUserId=" + ticketUserId
                + ", row=" + row
                + ", cell=" + cell
                + ", generalCell='" + generalCell + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket)) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return ticketFilmId == ticket.ticketFilmId && row == ticket.row && cell == ticket.cell;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketFilmId, row, cell);
    }
}
