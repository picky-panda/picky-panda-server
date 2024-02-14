package io.picky.panda.restaurant.infrastructure;

import io.picky.panda.restaurant.domain.SavedRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavedRestaurantRepository extends JpaRepository<SavedRestaurant, Long> {

    // READ
    Boolean existsByUserIdAndRestaurantId(Long userId, Long restaurantId);
    Optional<SavedRestaurant> findByUserIdAndRestaurantId(Long userId, Long restaurantId);
}
