package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.model.Ticket;

@ThreadSafe
@Controller
public class TicketController {
    private static final String FAIL = "Это место уже купили, выберите другое";
    private final TicketService ticketService;
    private final FilmService filmService;
    private final HallService hallService;

    public TicketController(TicketService ticketService, FilmService filmService,
                            HallService hallService) {
        this.ticketService = ticketService;
        this.filmService = filmService;
        this.hallService = hallService;
    }

    @PostMapping("/createTicket")
    public String createTicket(Model model, @ModelAttribute Ticket ticket, HttpSession session) {
        String result;
        setUser(model, session);
        Optional<Ticket> t = ticketService.addTicket(ticket);
        if (t.isPresent()) {
            model.addAttribute("m", hallService.getHall(t.get().getTicketFilmId()));
            model.addAttribute("film", filmService.findById(t.get().getTicketFilmId()));
            result = "hall";
        } else {
            model.addAttribute("message", FAIL);
            result = "notconfirm";
        }
        return result;
    }

    @PostMapping("/confirmBuyTicket")
    public String confirmBuyTicket(Model model,
                                   @ModelAttribute Ticket ticket, HttpSession session) {
        setUser(model, session);
        Ticket t = ticketService.setTicket(ticket);
        model.addAttribute("ticket", t);
        model.addAttribute("film", filmService.findById(t.getTicketFilmId()));
        return "buyticket";
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
