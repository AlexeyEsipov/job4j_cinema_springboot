package ru.job4j.cinema.controller;


import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.HallService;

import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
public class HallController {
    private final FilmService filmService;
    private final HallService hallService;

    public HallController(FilmService filmService, HallService hallService) {
        this.filmService = filmService;
        this.hallService = hallService;
    }

    @PostMapping("/getHall")
    public String getHall(Model model, @ModelAttribute Film film, HttpSession session) {
        setUser(model, session);
        model.addAttribute("m", hallService.getHall(film.getFilmId()));
        model.addAttribute("film", filmService.setFilmName(film));
        return "hall";
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
