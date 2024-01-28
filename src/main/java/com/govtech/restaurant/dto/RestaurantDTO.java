package com.govtech.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantDTO {
    @JsonProperty
    private Long restaurantId;

    @JsonProperty
    private String restaurantName;
}
