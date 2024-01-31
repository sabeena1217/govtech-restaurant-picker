package com.govtech.restaurant.controller;

import com.govtech.restaurant.dao.RestaurantDAO;
import com.govtech.restaurant.dto.RestaurantDTO;
import com.govtech.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody RestaurantDTO restaurantDTO) {
        try {
            RestaurantDAO newRestaurant = RestaurantDAO.builder().restaurantName(restaurantDTO.getRestaurantName()).build();
            restaurantService.addRestaurant(newRestaurant);
            return new ResponseEntity<>("Restaurant added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding restaurant: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{restaurantId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long restaurantId) {
        try {
            restaurantService.deleteRestaurantById(restaurantId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
