package com.govtech.restaurant.common;

import com.govtech.restaurant.dto.RestaurantDTO;
import lombok.experimental.UtilityClass;
import com.govtech.restaurant.dto.UserDTO;
import com.govtech.restaurant.dao.UserDAO;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@UtilityClass
public class UserUtil {

     public UserDTO mapDAOToDTO(UserDAO restaurantDAO) {
        return UserDTO.builder()
                .userId(restaurantDAO.getUserId())
                .username(restaurantDAO.getUsername())
                .build();
    }

}
