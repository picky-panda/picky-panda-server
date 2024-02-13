package io.picky.panda.restaurant.application;

import io.picky.panda.exception.ErrorCode;
import io.picky.panda.exception.model.ConflictException;
import io.picky.panda.exception.model.NotFoundException;
import io.picky.panda.restaurant.infrastructure.RestaurantRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestaurantServiceUtils {

    public static void isAlreadySaveRestaurant(RestaurantRepository restaurantRepository, Double latitude, Double longitude, String address) {
        if (restaurantRepository.existsByLatitudeAndLongitude(latitude, longitude)) {
            throw new ConflictException(ErrorCode.ALREADY_REGISTER_RESTAURANT);
        }

        if (restaurantRepository.existsByAddress(address)) {
            throw new ConflictException(ErrorCode.ALREADY_REGISTER_RESTAURANT);
        }
    }

    public static void isExistsRestaurant(RestaurantRepository restaurantRepository, Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new NotFoundException(ErrorCode.UNREGISTERED_RESTAURANT);
        }
    }
}
