package com.govtech.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.govtech.restaurant.dao.RestaurantDAO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    @JsonProperty
    private Long userId;

    @JsonProperty
    private String username;

    @JsonProperty
    private String restaurantPreferenceName;

    private boolean selected;
}
