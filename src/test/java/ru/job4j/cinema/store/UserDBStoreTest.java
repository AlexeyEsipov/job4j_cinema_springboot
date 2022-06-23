package ru.job4j.cinema.store;

import org.junit.After;
import org.junit.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.User;

import java.util.Optional;

import static org.junit.Assert.*;

public class UserDBStoreTest {

    private static final UserDBStore USER_DB_STORE = new UserDBStore(new Main().loadPool());
    private static final User USER = new User(0, "1", "1", "1");

    @After
    public void clearStoreAfter() {
        USER_DB_STORE.deleteAllUsers();
    }

    @Test
    public void whenAddAndGetUser() {
        Optional<User> userToDB = USER_DB_STORE.add(USER);
        assertTrue(userToDB.isPresent());
        Optional<User> userFromDB = USER_DB_STORE.get(USER.getEmail(), USER.getPhone());
        assertTrue(userFromDB.isPresent());
        assertEquals(USER, userFromDB.get());
    }

    @Test
    public void whenAddUserWithSameEmailPhone() {
        Optional<User> userToDB = USER_DB_STORE.add(USER);
        assertTrue(userToDB.isPresent());
        Optional<User> userFromDB = USER_DB_STORE.get(USER.getEmail(), USER.getPhone());
        assertTrue(userFromDB.isPresent());
        assertEquals(USER, userFromDB.get());
        User userSame = new User(0, "1", "1", "1");
        Optional<User> userSameToDB = USER_DB_STORE.add(userSame);
        assertTrue(userSameToDB.isEmpty());
    }
}