package com.govtech.restaurant.controller;

import com.govtech.restaurant.service.RestaurantService;
import com.govtech.restaurant.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UserSessionController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

        if (messages == null) {
            messages = new ArrayList<>();
        }
        model.addAttribute("sessionMessages", messages);

        return "index";
    }

//    @PostMapping("/persistMessage")
//    public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
//        @SuppressWarnings("unchecked")
//        List<String> msgs = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
//        if (msgs == null) {
//            msgs = new ArrayList<>();
//            request.getSession().setAttribute("MY_SESSION_MESSAGES", msgs);
//        }
//        msgs.add(msg);
//        request.getSession().setAttribute("MY_SESSION_MESSAGES", msgs);
//        return "redirect:/";
//    }

    @PostMapping("/persistMessage")
    public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        List<String> msgs = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
        if (msgs == null) {
            msgs = new ArrayList<>();
            request.getSession().setAttribute("MY_SESSION_MESSAGES", msgs);
        }
        msgs.add(msg);
        request.getSession().setAttribute("MY_SESSION_MESSAGES", msgs);
        return "redirect:/";
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

    @ModelAttribute("users")
    public String getUser(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/";
    }

    @PostMapping("/chooseRestaurant/{userId}")
    public String updateUser(@PathVariable Long userId) {
        userService.updateUser(userId);
        return "redirect:/";
    }
}
