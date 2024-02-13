package io.picky.panda.restaurant.infrastructure;

import io.picky.panda.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // READ
    Boolean existsByLatitudeAndLongitude(Double latitude, Double longitude);
    Boolean existsByAddress(String address);
}
