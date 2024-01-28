package com.govtech.restaurant.service;

import com.govtech.restaurant.common.Constants;
import com.govtech.restaurant.common.UserUtil;
import com.govtech.restaurant.dao.UserDAO;
import com.govtech.restaurant.dto.RestaurantDTO;
import com.govtech.restaurant.dto.UserDTO;
import com.govtech.restaurant.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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

    private void saveRestaurantChoiceOfUser(Long userId, Long restaurantId) {
        UserDAO userById = getUserById(userId);
        userById.setRestaurant(restaurantService.getRestaurantById(restaurantId));
    }

    public void setRandomRestaurantSelectionToUser(UserDTO userDTO) {
        List<RestaurantDTO> restaurants = restaurantService.getAllRestaurants();
        if (!restaurants.isEmpty()) {
            Collections.shuffle(restaurants, new Random());
            RestaurantDTO randomRestaurant = restaurants.get(0);
            userDTO.setRestaurantPreferenceName(randomRestaurant.getRestaurantName());
            saveRestaurantChoiceOfUser(userDTO.getUserId(), randomRestaurant.getRestaurantId());
        } else {
            userDTO.setRestaurantPreferenceName(Constants.NO_IDEA);
            saveRestaurantChoiceOfUser(userDTO.getUserId(), 0L);
        }
    }

    public void updateUser(Long userId) {
        UserDAO userById = getUserById(userId);
        UserDTO userDTO = UserUtil.mapDAOToDTO(userById);
        setRandomRestaurantSelectionToUser(userDTO);
    }
}
