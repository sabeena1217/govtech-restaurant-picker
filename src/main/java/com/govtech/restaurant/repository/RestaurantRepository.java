package com.govtech.restaurant.repository;

import com.govtech.restaurant.dao.RestaurantDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantDAO, Long> {
}
