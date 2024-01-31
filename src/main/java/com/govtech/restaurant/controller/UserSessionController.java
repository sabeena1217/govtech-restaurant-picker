package com.govtech.restaurant.controller;

import com.govtech.restaurant.dto.UserDTO;
import com.govtech.restaurant.service.RestaurantService;
import com.govtech.restaurant.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.govtech.restaurant.common.Constants.*;

@Slf4j
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
        // update users in session
        request.getSession().setAttribute(SESSION_ATTRIBUTE_USERS, usersInSession);
        model.addAttribute(SESSION_ATTRIBUTE_USERS, usersInSession);

        List<String> restaurantsSelections = usersInSession.stream()
                .filter(user -> user.getRestaurantPreferenceName() != null && !user.getRestaurantPreferenceName().isEmpty())
                .map(UserDTO::getRestaurantPreferenceName)
                .toList();
        // update restaurant selection in session
        request.getSession().setAttribute(SESSION_ATTRIBUTE_RESTAURANTS, restaurantsSelections);
        model.addAttribute(SESSION_ATTRIBUTE_RESTAURANTS, restaurantsSelections);
        log.info("Selected Restaurants | {}", restaurantsSelections.stream().collect(Collectors.joining(",")));
        return "index";
    }

    @PostMapping("/destroy")
    public String destroySession(Model model, HttpServletRequest request) {

        List<String> restaurantsSelections = (List<String>) request.getSession().getAttribute(SESSION_ATTRIBUTE_RESTAURANTS);
        Collections.shuffle(restaurantsSelections, new Random());
        String finalizedRestaurant = restaurantsSelections.get(0);
        request.getSession().setAttribute(FINALIZED_RESTAURANT, finalizedRestaurant);
        model.addAttribute(FINALIZED_RESTAURANT, finalizedRestaurant);
        request.getSession().invalidate();
        return "redirect:/";
    }

//    @PostMapping("/chooseRestaurant/{userId}")
//    public String chooseRestaurant(
//            @PathVariable Long userId,
//            @RequestParam(name = "invited", required = false) Boolean invited) {
//        log.info("test test " + userId + " " + invited);
//        userService.updateUser(userId);
//        return "redirect:/";
//    }

    @PostMapping("/chooseRestaurant/{userId}")
    public String chooseRestaurant(
            @PathVariable Long userId,
            @RequestParam(name = "invited", required = false) Boolean invited, Model model, HttpServletRequest request) {
        if (invited) {
            log.info("User Id:{} is invited...", userId);
        } else {
            log.info("User Id:{} invitation reverted...", userId);
        }

        userService.updateUser(userId, invited);

//
//        request.getSession().setAttribute(SESSION_ATTRIBUTE_USERS, usersInSession);
//        model.addAttribute(SESSION_ATTRIBUTE_USERS, usersInSession);
//
        return "redirect:/";
//        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
