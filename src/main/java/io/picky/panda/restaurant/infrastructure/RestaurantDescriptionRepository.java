package io.picky.panda.restaurant.infrastructure;

import io.picky.panda.restaurant.domain.RestaurantDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantDescriptionRepository extends JpaRepository<RestaurantDescription, Long> {
}
