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
import java.util.Optional;

import ru.job4j.cinema.model.User;

@ThreadSafe
@Repository
public class UserDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(UserDBStore.class.getName());
    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO users (username, email, phone) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setUserId(id.getInt(1));
                    result = Optional.of(user);
                }
            }
        } catch (SQLException e) {
            LOG.error("Exception in UserDBStore#add(User user)", e);
        }
        return result;
    }

    public Optional<User> get(String email, String phone) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM users WHERE email = ? and phone = ?")
        ) {
            ps.setString(1, email);
            ps.setString(2, phone);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = Optional.of(new User(
                            it.getInt("id"),
                            it.getString("username"),
                            it.getString("email"),
                            it.getString("phone")
                    ));
                }
            }
        } catch (SQLException e) {
            LOG.error("Exception in UserDBStore#get(String email, String phone)", e);
        }
        return result;
    }

    public boolean deleteAllUsers() {
        boolean result = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "DELETE FROM users;")) {
            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
