package com.govtech.restaurant.repository;

import com.govtech.restaurant.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDAO, Long> {
}
