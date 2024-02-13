package io.picky.panda.restaurant.application;

import io.picky.panda.auth.application.UserServiceUtils;
import io.picky.panda.auth.infrastructure.UserRepository;
import io.picky.panda.restaurant.domain.Restaurant;
import io.picky.panda.restaurant.domain.RestaurantDescription;
import io.picky.panda.restaurant.infrastructure.RestaurantDescriptionRepository;
import io.picky.panda.restaurant.infrastructure.RestaurantRepository;
import io.picky.panda.restaurant.ui.dto.RestaurantRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantDescriptionRepository restaurantDescriptionRepository;

    @Transactional
    public void saveRestaurant(Long userId, RestaurantRequest request) {
        UserServiceUtils.existsUserById(userRepository, userId);
        RestaurantServiceUtils.isAlreadySaveRestaurant(restaurantRepository, request.latitude(), request.longitude(), request.address());

        Restaurant restaurant = restaurantRepository.save(Restaurant.builder()
                .image("test")
                .placeName(request.placeName())
                .address(request.address())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .category(request.category())
                .options(request.options())
                .build()
        );

        if (request.description() != null) {
            restaurantDescriptionRepository.save(RestaurantDescription.builder()
                    .restaurantId(restaurant.getId())
                    .description(request.description())
                    .build()
            );
        }
    }
}
