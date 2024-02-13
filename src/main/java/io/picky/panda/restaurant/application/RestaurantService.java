package io.picky.panda.restaurant.application;

import io.picky.panda.auth.application.UserServiceUtils;
import io.picky.panda.auth.infrastructure.UserRepository;
import io.picky.panda.exception.ErrorCode;
import io.picky.panda.exception.model.NotFoundException;
import io.picky.panda.restaurant.domain.Restaurant;
import io.picky.panda.restaurant.domain.RestaurantDescription;
import io.picky.panda.restaurant.infrastructure.RestaurantDescriptionRepository;
import io.picky.panda.restaurant.infrastructure.RestaurantRepository;
import io.picky.panda.restaurant.infrastructure.SavedRestaurantRepository;
import io.picky.panda.restaurant.ui.dto.RestaurantDescriptionResponse;
import io.picky.panda.restaurant.ui.dto.RestaurantRequest;
import io.picky.panda.restaurant.ui.dto.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final SavedRestaurantRepository savedRestaurantRepository;
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

    @Transactional
    public RestaurantResponse getRestaurant(Long userId, Long restaurantId) {
        UserServiceUtils.existsUserById(userRepository, userId);

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_RESTAURANT));

        List<RestaurantDescriptionResponse> descriptions = restaurantDescriptionRepository.findAllByRestaurantId(restaurantId).stream().map(
                d -> RestaurantDescriptionResponse.builder()
                        .description(d.getDescription())
                        .isAgreed(d.getIsAgreed())
                        .isDisagreed(d.getIsDisagreed())
                        .build()
        ).toList();



        return RestaurantResponse.builder()
                .isSaved(savedRestaurantRepository.existsByUserIdAndRestaurantId(userId, restaurantId))
                .image(restaurant.getImage())
                .placeName(restaurant.getPlaceName())
                .latitude(restaurant.getLatitude())
                .longitude(restaurant.getLongitude())
                .descriptions(descriptions)
                .build();
    }
}
