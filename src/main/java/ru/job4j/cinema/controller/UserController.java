package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

@ThreadSafe
@Controller
public class UserController {

    private static final String FAIL = "Пользователь с такой почтой уже существует";
    private static final String SUCCESS =
            "Регистрация прошла успешно, для продолжения работы авторизуйтесь!";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/formRegistration")
    public String formRegistration(Model model, HttpSession session) {
        setUser(model, session);
        return "registration";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model,
                            @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        String result;
        Optional<User> userDb = userService.get(
                user.getEmail(), user.getPhone());
        if (userDb.isEmpty()) {
            result = "redirect:/loginPage?fail=true";
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("user", userDb.get());
            result = "redirect:/index";
        }
        return result;
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user, HttpSession session) {
        String result;
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message", FAIL);
            result = "fail";
        } else {
            setUser(model, session);
            model.addAttribute("message", SUCCESS);
            result = "success";
        }
        return result;
    }

    @PostMapping("/successRedirect")
    public String successRedirect(Model model, HttpSession session) {
        setUser(model, session);
        return "redirect:/loginPage";
    }

    private void setUser(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUserName("Гость");
        }
        model.addAttribute("user", user);
    }
}