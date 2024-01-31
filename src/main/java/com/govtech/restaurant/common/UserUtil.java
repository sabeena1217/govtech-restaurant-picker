package com.govtech.restaurant.common;

import com.govtech.restaurant.dao.UserDAO;
import com.govtech.restaurant.dto.UserDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserUtil {

    public UserDTO mapDAOToDTO(UserDAO userDAO) {
        return UserDTO.builder()
                .userId(userDAO.getUserId())
                .username(userDAO.getUsername())
                .restaurantPreferenceName(userDAO.getRestaurant() != null ? userDAO.getRestaurant().getRestaurantName() : null)
                .build();
    }

}
