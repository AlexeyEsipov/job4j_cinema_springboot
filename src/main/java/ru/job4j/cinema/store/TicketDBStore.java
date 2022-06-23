package ru.job4j.cinema.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@ThreadSafe
@Repository
public class TicketDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(TicketDBStore.class.getName());
    private final BasicDataSource pool;

    public TicketDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<Ticket> addTicket(Ticket ticket) {
        Optional<Ticket> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO ticket (film_id, pos_row, cell, user_id) VALUES (?, ?, ?, ?);",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, ticket.getTicketFilmId());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getTicketUserId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setTicketId(id.getInt(1));
                    result = Optional.of(ticket);
                }
            }
        } catch (SQLException e) {
            LOG.error("Exception in TicketDBStore#addTicket(Ticket ticket)", e);
        }
        return result;
    }

    public Collection<Ticket> getAllTicketForFilm(int filmIfd) {
        Collection<Ticket> result = new HashSet<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM ticket WHERE film_id = ?;")) {
            ps.setInt(1, filmIfd);
            ps.execute();
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Ticket ticket = new Ticket(
                            it.getInt("film_id"),
                            it.getInt("pos_row"),
                            it.getInt("cell")
                    );
                    result.add(ticket);
                }
            }
        } catch (SQLException e) {
            LOG.error("Exception in TicketDBStore#getAllTicketForFilm(int filmIfd)", e);
        }
        return result;
    }

    public void deleteTicket(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "DELETE FROM ticket WHERE id = ?;")) {
            ps.setInt(1, ticket.getTicketId());
            ps.execute();
        } catch (SQLException e) {
            LOG.error("Exception in TicketDBStore#deleteTicket(Ticket ticket)", e);
        }
    }

    public boolean deleteAllTicket() {
        boolean result = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "DELETE FROM ticket;")) {
            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Exception in TicketDBStore#deleteAllTicket()", e);
        }
        return result;
    }
}
