package io.picky.panda.restaurant.infrastructure;

import io.picky.panda.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // READ
    Boolean existsByLatitudeAndLongitude(Double latitude, Double longitude);
    Boolean existsByAddress(String address);
    @Query(value = "SELECT * FROM restaurant WHERE latitude BETWEEN ?3 AND ?1 AND longitude BETWEEN ?4 AND ?2", nativeQuery = true)
    List<Restaurant> findWithinArea(Long northEastLatitude, Long northEastLongitude, Long southWestLatitude, Long southWestLongitude);
}
