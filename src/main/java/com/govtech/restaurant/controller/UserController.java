package com.govtech.restaurant.controller;

import com.govtech.restaurant.dao.UserDAO;
import com.govtech.restaurant.dto.UserDTO;
import com.govtech.restaurant.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
@Tag(name = "User", description = "User Management APIs")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @Operation(summary = "Add user", description = "This endpoint allows you to add users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Response", content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal System Error", content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")),
    })
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        try {
            UserDAO newUser = UserDAO.builder().username(userDTO.getUsername()).build();
            userService.addUser(newUser);
            return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{userId}")
    @Operation(summary = "Delete user", description = "This endpoint allows you to delete users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Response", content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal System Error", content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")),
    })
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUserById(userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
