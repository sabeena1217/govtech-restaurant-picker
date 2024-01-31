package com.govtech.restaurant.controller;

import com.govtech.restaurant.aop.annotation.SessionInfoInjector;
import com.govtech.restaurant.dto.UserDTO;
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

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.govtech.restaurant.common.Constants.*;

@Slf4j
@Controller
public class SessionController {

    @Autowired
    private UserService userService;

    @SessionInfoInjector(sessionId = "[1].session.id")
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        // update users in session
        List<UserDTO> usersInSession = userService.getAllUsers();
        request.getSession().setAttribute(SESSION_ATTRIBUTE_USERS, usersInSession);
        model.addAttribute(SESSION_ATTRIBUTE_USERS, usersInSession);

        // update restaurant selections in session
        List<String> restaurantsSelections = usersInSession.stream()
                .filter(user -> user.getRestaurantPreferenceName() != null && !user.getRestaurantPreferenceName().isEmpty())
                .map(UserDTO::getRestaurantPreferenceName)
                .toList();
        request.getSession().setAttribute(SESSION_ATTRIBUTE_RESTAURANTS, restaurantsSelections);
        model.addAttribute(SESSION_ATTRIBUTE_RESTAURANTS, restaurantsSelections);
        log.info("Selected Restaurants | {}", restaurantsSelections.stream().collect(Collectors.joining(",")));
        return "index";
    }

    @SessionInfoInjector(sessionId = "[1].session.id")
    @PostMapping("/destroy")
    public String destroySession(Model model, HttpServletRequest request) {

        List<String> restaurantsSelections = (List<String>) request.getSession().getAttribute(SESSION_ATTRIBUTE_RESTAURANTS);
        if (restaurantsSelections != null) {

            // Create a Random object
            Random random = new Random();
            // Generate a random index within the range of the list size
            int randomIndex = random.nextInt(restaurantsSelections.size());
            // Retrieve the random value using the random index
            String finalizedRestaurant = restaurantsSelections.get(randomIndex);

            // update the finalized restaurant in session
            request.getSession().setAttribute(FINALIZED_RESTAURANT, finalizedRestaurant);
            model.addAttribute(FINALIZED_RESTAURANT, finalizedRestaurant);

            // clear selections of users
            userService.clearRestaurantSelectionsOfAllUsers();

            log.info("Finalized Restaurant:{}", finalizedRestaurant);
            request.getSession().invalidate();
        }
        return "redirect:/";
    }

    @SessionInfoInjector(sessionId = "[2].session.id")
    @PostMapping("/chooseRestaurant/{userId}")
    public String chooseRestaurant(@PathVariable Long userId,
                                   @RequestParam(name = "invited", required = false) Boolean invited,
                                   HttpServletRequest request) {
        if (invited) log.info("User Id:{} is invited...", userId);
        else log.info("User Id:{} invitation reverted...", userId);
        userService.updateUser(userId, invited);
        return "redirect:/";
    }
}
