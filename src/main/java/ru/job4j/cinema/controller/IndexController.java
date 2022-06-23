package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmService;

@ThreadSafe
@Controller
public class IndexController {

    private final FilmService filmService;

    public IndexController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        setUser(model, session);
        model.addAttribute("films", filmService.findAll());
        return "index";
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
