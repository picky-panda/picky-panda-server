package io.picky.panda.restaurant.infrastructure;

import io.picky.panda.restaurant.domain.RestaurantDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantDescriptionRepository extends JpaRepository<RestaurantDescription, Long> {

    // READ
    List<RestaurantDescription> findAllByRestaurantId(Long restaurantId);
    Integer countByUserId(Long userId);
    List<RestaurantDescription> findAllByUserId(Long userId);
}
