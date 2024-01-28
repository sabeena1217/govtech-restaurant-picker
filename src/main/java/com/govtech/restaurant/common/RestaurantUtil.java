package com.govtech.restaurant.common;

import com.govtech.restaurant.dao.RestaurantDAO;
import com.govtech.restaurant.dto.RestaurantDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RestaurantUtil {

    public RestaurantDTO mapDAOToDTO(RestaurantDAO userDAO) {
        return RestaurantDTO.builder()
                .restaurantId(userDAO.getRestaurantId())
                .restaurantName(userDAO.getRestaurantName())
                .build();
    }

}
