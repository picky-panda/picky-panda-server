package io.picky.panda.restaurant.application;

import io.picky.panda.auth.application.UserServiceUtils;
import io.picky.panda.auth.infrastructure.UserRepository;
import io.picky.panda.exception.ErrorCode;
import io.picky.panda.exception.model.ConflictException;
import io.picky.panda.exception.model.NotFoundException;
import io.picky.panda.restaurant.domain.*;
import io.picky.panda.restaurant.infrastructure.*;
import io.picky.panda.restaurant.ui.dto.*;
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
    private final AgreedDescriptionRepository agreedDescriptionRepository;
    private final DisAgreedDescriptionRepository disAgreedDescriptionRepository;

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
                    .userId(userId)
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
                        .descriptionId(d.getId())
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

    @Transactional
    public List<RestaurantMapResponse> getRestaurantList(Long userId, Long northEastX, Long northEastY, Long southWestX, Long southWestY) {

        UserServiceUtils.existsUserById(userRepository, userId);

        return restaurantRepository.findWithinArea(northEastX, northEastY, southWestX, southWestY).stream().map(r ->
                RestaurantMapResponse.builder()
                        .id(r.getId())
                        .image(r.getImage())
                        .placeName(r.getPlaceName())
                        .latitude(r.getLatitude())
                        .longitude(r.getLongitude())
                        .build()
        ).toList();
    }

    @Transactional
    public void bookmarkRestaurant(Long userId, Long restaurantId, String status) {

        UserServiceUtils.existsUserById(userRepository, userId);
        RestaurantServiceUtils.isExistsRestaurant(restaurantRepository, restaurantId);

        if (status.equals("SAVE")) {
            if (savedRestaurantRepository.existsByUserIdAndRestaurantId(userId, restaurantId)) {
                throw new ConflictException(ErrorCode.ALREADY_BOOKMARK_RESTAURANT);
            }

            savedRestaurantRepository.save(SavedRestaurant.builder()
                    .userId(userId)
                    .restaurantId(restaurantId)
                    .build()
            );
        }

        if (status.equals("UNSAVE")) {
            if (!savedRestaurantRepository.existsByUserIdAndRestaurantId(userId, restaurantId)) {
                throw new NotFoundException(ErrorCode.UNSAVED_RESTAURANT);
            }

            SavedRestaurant savedRestaurant = savedRestaurantRepository.findByUserIdAndRestaurantId(userId, restaurantId)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.UNSAVED_RESTAURANT));
            savedRestaurantRepository.delete(savedRestaurant);
        }
    }

    @Transactional
    public void agreeDescription(Long userId, Long descriptionId, AgreeDescriptionRequest request) {

        UserServiceUtils.existsUserById(userRepository, userId);
        RestaurantDescription restaurantDescription = restaurantDescriptionRepository.findById(descriptionId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_DESCRIPTION));

        if (request.status().equals("AGREE")) {
            if (request.isApply()) {
                if (agreedDescriptionRepository.existsByUserIdAndDescriptionId(userId, descriptionId)) {
                    throw new ConflictException(ErrorCode.ALREADY_AGREE_DESCRIPTION);
                }

                agreedDescriptionRepository.save(AgreedDescription.builder()
                        .userId(userId)
                        .descriptionId(descriptionId)
                        .build()
                );
                restaurantDescription.increaseAgree();
            } else {
                if (!agreedDescriptionRepository.existsByUserIdAndDescriptionId(userId, descriptionId)) {
                    throw new NotFoundException(ErrorCode.NOT_AGREE_DESCRIPTION);
                }

                AgreedDescription agreedDescription = agreedDescriptionRepository.findByUserIdAndDescriptionId(userId, descriptionId)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_DESCRIPTION));
                agreedDescriptionRepository.delete(agreedDescription);
                restaurantDescription.decreaseAgree();
            }
        }

        if (request.status().equals("DISAGREE")) {
            if (request.isApply()) {
                if (disAgreedDescriptionRepository.existsByUserIdAndDescriptionId(userId, descriptionId)) {
                    throw new ConflictException(ErrorCode.ALREADY_DISAGREE_DESCRIPTION);
                }

                disAgreedDescriptionRepository.save(DisAgreedDescription.builder()
                        .userId(userId)
                        .descriptionId(descriptionId)
                        .build()
                );
                restaurantDescription.increaseDisAgree();
            } else {
                if (!disAgreedDescriptionRepository.existsByUserIdAndDescriptionId(userId, descriptionId)) {
                    throw new NotFoundException(ErrorCode.NOT_DISAGREE_DESCRIPTION);
                }

                DisAgreedDescription disAgreedDescription = disAgreedDescriptionRepository.findByUserIdAndDescriptionId(userId, descriptionId)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_DESCRIPTION));
                disAgreedDescriptionRepository.delete(disAgreedDescription);
                restaurantDescription.decreaseDisAgree();
            }
        }
    }

    @Transactional
    public void saveDescription(Long userId, Long restaurantId, String description) {

        UserServiceUtils.existsUserById(userRepository, userId);
        RestaurantServiceUtils.isExistsRestaurant(restaurantRepository, restaurantId);

        restaurantDescriptionRepository.save(RestaurantDescription.builder()
                .userId(userId)
                .restaurantId(restaurantId)
                .description(description)
                .build()
        );
    }
}
