package com.govtech.restaurant.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;

//    @OneToMany(cascade = CascadeType.ALL)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="restaurantId")
//    private List<RestaurantDAO> restaurants = new ArrayList<>();
    private RestaurantDAO restaurant;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
