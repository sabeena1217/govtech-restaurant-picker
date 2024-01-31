package com.govtech.restaurant.service;

import com.govtech.restaurant.common.RestaurantUtil;
import com.govtech.restaurant.common.UserUtil;
import com.govtech.restaurant.dao.RestaurantDAO;
import com.govtech.restaurant.dao.UserDAO;
import com.govtech.restaurant.dto.RestaurantDTO;
import com.govtech.restaurant.dto.UserDTO;
import com.govtech.restaurant.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private RestaurantService restaurantService;

    public void addUser(UserDAO newUser) {
        userRepository.save(newUser);
    }

    public List<UserDTO> getAllUsers() {
        List<UserDAO> daoList = userRepository.findAll();
        return daoList.stream()
                .map(UserUtil::mapDAOToDTO)
                .collect(Collectors.toList());
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserDAO getUserById(Long userId) {
        Optional<UserDAO> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new EntityNotFoundException("User with ID " + userId + " not found");
        }
    }

    public void updateUser(Long userId, Boolean invited) {
        if (invited) {
            setRandomRestaurantSelectionToUser(userId);
        } else {
            saveRestaurantChoiceOfUser(userId, null);
        }
    }

    public void setRandomRestaurantSelectionToUser(Long userId) {
        // retrieve all the available restaurants from DB
        List<RestaurantDTO> restaurants = restaurantService.getAllRestaurants();
        if (!restaurants.isEmpty()) {
            Collections.shuffle(restaurants, new Random());
            RestaurantDTO randomRestaurant = restaurants.get(0);
            saveRestaurantChoiceOfUser(userId, randomRestaurant);
        } else {
            log.warn("No restaurants available...");
        }
    }

    private void saveRestaurantChoiceOfUser(Long userId, RestaurantDTO restaurantDTO) {
        UserDAO userDAO = getUserById(userId);
        RestaurantDAO restaurantDAO = null;
        if (restaurantDTO != null) {
            restaurantDAO = RestaurantUtil.mapDTOToDAO(restaurantDTO);
            log.info("User Id:{} | Selected restaurant:{}", userId, restaurantDTO.getRestaurantName());
        } else {
            log.info("User Id:{} | Restaurant selection reverted..", userId);
        }
        userDAO.setRestaurant(restaurantDAO);
        userRepository.save(userDAO);
    }

    public void clearRestaurantSelectionsOfAllUsers() {
        List<UserDAO> allUsers = userRepository.findAll();
        allUsers.forEach(user -> user.setRestaurant(null));
        userRepository.saveAll(allUsers);
    }

}
