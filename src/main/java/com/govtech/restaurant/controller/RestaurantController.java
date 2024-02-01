package com.govtech.restaurant.controller;

import com.govtech.restaurant.dao.RestaurantDAO;
import com.govtech.restaurant.dto.RestaurantDTO;
import com.govtech.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/restaurant")
@Tag(name = "Restaurant", description = "Restaurant Management APIs")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/add")
    @Operation(summary = "Add restaurant", description = "This endpoint allows you to add restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Response", content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal System Error", content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")),
    })
    public ResponseEntity<String> addRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        try {
            RestaurantDAO newRestaurant = RestaurantDAO.builder().restaurantName(restaurantDTO.getRestaurantName()).build();
            restaurantService.addRestaurant(newRestaurant);
            return new ResponseEntity<>("Restaurant added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding restaurant: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{restaurantId}")
    @Operation(summary = "Delete restaurant", description = "This endpoint allows you to delete restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Response", content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal System Error", content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")),
    })
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long restaurantId) {
        try {
            restaurantService.deleteRestaurantById(restaurantId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
