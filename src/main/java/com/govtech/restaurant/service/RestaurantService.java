package com.govtech.restaurant.service;

import com.govtech.restaurant.common.RestaurantUtil;
import com.govtech.restaurant.dao.RestaurantDAO;
import com.govtech.restaurant.dto.RestaurantDTO;
import com.govtech.restaurant.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    public void addRestaurant(RestaurantDAO newUser) {
        restaurantRepository.save(newUser);
    }

    public List<RestaurantDTO> getAllRestaurants() {
        List<RestaurantDAO> daoList = restaurantRepository.findAll();
        return daoList.stream()
                .map(RestaurantUtil::mapDAOToDTO)
                .collect(Collectors.toList());
    }

    public void deleteRestaurantById(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    public RestaurantDAO getRestaurantById(Long restaurantId) {
        Optional<RestaurantDAO> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isPresent()) {
            return restaurantOptional.get();
        } else {
            throw new EntityNotFoundException("Restaurant with ID " + restaurantId + " not found");
        }
    }
}
