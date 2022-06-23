package ru.job4j.cinema.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    private static final String FAIL = "Пользователь с такой почтой уже существует";
    private static final String SUCCESS =
            "Регистрация прошла успешно, для продолжения работы авторизуйтесь!";

    @Test
    public void whenFormRegistration() {
        User user = new User();
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        when(session.getAttribute("user")).thenReturn(user);
        String page = userController.formRegistration(model, session);
        assertEquals("registration", page);
        verify(model).addAttribute("user", user);
    }

    @Test
    public void whenLoginPage() {
        Boolean fail = false;
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.loginPage(model, fail);
        assertEquals("login", page);
        verify(model).addAttribute("fail", true);
    }

    @Test
    public void whenLogout() {
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.logout(session);
        assertEquals("redirect:/index", page);
    }

    @Test
    public void whenLoginFalse() {
        User user = new User();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        when(userService.get(user.getEmail(), user.getPhone())).thenReturn(Optional.empty());
        String page = userController.login(user, httpServletRequest);
        assertEquals("redirect:/loginPage?fail=true", page);
    }

    @Test
    public void whenLoginTrue() {
        User user = new User();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        UserService userService = mock(UserService.class);
        HttpSession session = mock(HttpSession.class);
        UserController userController = new UserController(userService);
        when(userService.get(user.getEmail(), user.getPhone())).thenReturn(Optional.of(user));
        when(httpServletRequest.getSession()).thenReturn(session);
        String page = userController.login(user, httpServletRequest);
        assertEquals("redirect:/index", page);
    }

    @Test
    public void whenRegistrationFail() {
        User user = new User();
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        when(userService.add(user)).thenReturn(Optional.empty());
        String page = userController.registration(model, user, session);
        assertEquals("fail", page);
        verify(model).addAttribute("message", FAIL);
    }

    @Test
    public void whenRegistrationSuccess() {
        User user = new User();
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        when(userService.add(user)).thenReturn(Optional.of(user));
        String page = userController.registration(model, user, session);
        assertEquals("success", page);
        verify(model).addAttribute("message", SUCCESS);
        verify(model).addAttribute("user", user);
    }

    @Test
    public void whenSuccessRedirect() {
        User user = new User();
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        when(session.getAttribute("user")).thenReturn(user);
        String page = userController.successRedirect(model, session);
        assertEquals("redirect:/loginPage", page);
        verify(model).addAttribute("user", user);
    }
}