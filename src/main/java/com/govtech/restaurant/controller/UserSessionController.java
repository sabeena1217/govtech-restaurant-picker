package com.govtech.restaurant.controller;

import com.govtech.restaurant.dto.UserDTO;
import com.govtech.restaurant.service.RestaurantService;
import com.govtech.restaurant.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static com.govtech.restaurant.common.Constants.SESSION_ATTRIBUTE_USERS;


@Controller
public class UserSessionController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        List<UserDTO> usersInSession = (List<UserDTO>) request.getSession().getAttribute(SESSION_ATTRIBUTE_USERS);
//        if (usersInSession == null) {
            usersInSession = userService.getAllUsers();
//        }
        request.getSession().setAttribute(SESSION_ATTRIBUTE_USERS, usersInSession);
        model.addAttribute(SESSION_ATTRIBUTE_USERS, usersInSession);

        return "index";
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

    @PostMapping("/chooseRestaurant/{userId}")
    public String updateUser(@PathVariable Long userId) {
        userService.updateUser(userId);
        return "redirect:/";
    }
}
