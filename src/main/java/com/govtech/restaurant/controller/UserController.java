package com.govtech.restaurant.controller;

import com.govtech.restaurant.dao.UserDAO;
import com.govtech.restaurant.dto.UserDTO;
import com.govtech.restaurant.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        try {
            UserDAO newUser = UserDAO.builder().username(userDTO.getUsername()).build();
            userService.addUser(newUser);
            return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//
//    @GetMapping("/get")
//    public ResponseEntity<List<UserDTO>> getUser() {
//        List<UserDTO> allUsers = userService.getAllUsers();
//        return new ResponseEntity<>(allUsers, HttpStatus.CREATED);
//    }


    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUserById(userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/chooseRestaurant/{userId}")
//    public ResponseEntity<String> chooseRestaurant(
//            @PathVariable Long userId,
//            @RequestParam(name = "invited", required = false) Boolean invited) {
//        log.info("test test " + userId + " " + invited);
//        userService.updateUser(userId, invited);
//        return new ResponseEntity<>("",HttpStatus.OK);
//    }

}
