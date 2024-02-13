package io.picky.panda.restaurant.infrastructure;

import io.picky.panda.restaurant.domain.SavedRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedRestaurantRepository extends JpaRepository<SavedRestaurant, Long> {

    // READ
    Boolean existsByUserIdAndRestaurantId(Long userId, Long restaurantId);
}
