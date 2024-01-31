package com.govtech.restaurant.common;

import com.govtech.restaurant.dao.RestaurantDAO;
import com.govtech.restaurant.dto.RestaurantDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RestaurantUtil {

    public RestaurantDTO mapDAOToDTO(RestaurantDAO restaurantDAO) {
        return RestaurantDTO.builder()
                .restaurantId(restaurantDAO.getRestaurantId())
                .restaurantName(restaurantDAO.getRestaurantName())
                .build();
    }

    public RestaurantDAO mapDTOToDAO(RestaurantDTO restaurantDTO){
        return RestaurantDAO.builder()
                .restaurantId(restaurantDTO.getRestaurantId())
                .restaurantName(restaurantDTO.getRestaurantName())
                .build();
    }

}
