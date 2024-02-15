package io.picky.panda.auth.application;

import io.picky.panda.auth.domain.User;
import io.picky.panda.auth.infrastructure.UserRepository;
import io.picky.panda.auth.ui.dto.ProfileResponse;
import io.picky.panda.auth.ui.dto.RestaurantDto;
import io.picky.panda.exception.ErrorCode;
import io.picky.panda.exception.model.BadRequestException;
import io.picky.panda.exception.model.NotFoundException;
import io.picky.panda.restaurant.domain.AgreedDescription;
import io.picky.panda.restaurant.domain.Restaurant;
import io.picky.panda.restaurant.domain.RestaurantDescription;
import io.picky.panda.restaurant.domain.SavedRestaurant;
import io.picky.panda.restaurant.infrastructure.AgreedDescriptionRepository;
import io.picky.panda.restaurant.infrastructure.RestaurantDescriptionRepository;
import io.picky.panda.restaurant.infrastructure.RestaurantRepository;
import io.picky.panda.restaurant.infrastructure.SavedRestaurantRepository;
import io.picky.panda.review.domain.Review;
import io.picky.panda.review.infrastructure.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final SavedRestaurantRepository savedRestaurantRepository;
    private final AgreedDescriptionRepository agreedDescriptionRepository;
    private final RestaurantDescriptionRepository restaurantDescriptionRepository;

    @Transactional
    public ProfileResponse getProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_USER));

        Integer descriptionCount = restaurantDescriptionRepository.countByUserId(userId);
        Integer reviewCount = reviewRepository.countByUserId(userId);
        Integer savedCount = savedRestaurantRepository.countByUserId(userId);
        List<RestaurantDto> restaurantDtoList = agreedDescriptionRepository.findTop2ByUserIdOrderByCreatedAtDesc(userId).stream().map(d -> {
            RestaurantDescription restaurantDescription = restaurantDescriptionRepository.findById(d.getDescriptionId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_DESCRIPTION));
            Restaurant restaurant = restaurantRepository.findById(restaurantDescription.getRestaurantId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_RESTAURANT));

            return RestaurantDto.builder()
                    .id(restaurant.getId())
                    .restaurantImage(restaurant.getImage())
                    .placeName(restaurant.getPlaceName())
                    .address(restaurant.getAddress())
                    .options(restaurant.getOptions())
                    .build();
        }).toList();

        return ProfileResponse.builder()
                .profileImage(user.getProfileUrl())
                .email(user.getEmail())
                .myDescriptionCount(descriptionCount)
                .myReviewCount(reviewCount)
                .mySavedRestaurantCount(savedCount)
                .recentlyEvaluatedList(restaurantDtoList)
                .build();
    }

    @Transactional
    public List<RestaurantDto> getRecentlyEvaluated(Long userId, String section) {

        UserServiceUtils.existsUserById(userRepository, userId);

        if (section.equals("recentlyEvaluated")) {
            List<AgreedDescription> agreedDescriptions = agreedDescriptionRepository.findAllByUserId(userId);

            return agreedDescriptions.stream().map(d -> {
                RestaurantDescription restaurantDescription = restaurantDescriptionRepository.findById(d.getDescriptionId())
                        .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_DESCRIPTION));
                Restaurant restaurant = restaurantRepository.findById(restaurantDescription.getRestaurantId())
                        .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_RESTAURANT));

                return RestaurantDto.builder()
                        .id(restaurant.getId())
                        .restaurantImage(restaurant.getImage())
                        .placeName(restaurant.getPlaceName())
                        .address(restaurant.getAddress())
                        .options(restaurant.getOptions())
                        .build();
            }).toList();
        }

        if (section.equals("saved")) {
            List<SavedRestaurant> savedRestaurants = savedRestaurantRepository.findAllByUserId(userId);

            return savedRestaurants.stream().map(r -> {
                Restaurant restaurant = restaurantRepository.findById(r.getRestaurantId())
                        .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_RESTAURANT));

                return RestaurantDto.builder()
                        .id(restaurant.getId())
                        .restaurantImage(restaurant.getImage())
                        .placeName(restaurant.getPlaceName())
                        .address(restaurant.getAddress())
                        .options(restaurant.getOptions())
                        .build();
            }).toList();
        }

        if (section.equals("register")) {
            List<RestaurantDescription> restaurantDescriptions = restaurantDescriptionRepository.findAllByUserId(userId);

            return restaurantDescriptions.stream().map(r -> {
                Restaurant restaurant = restaurantRepository.findById(r.getRestaurantId())
                        .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_RESTAURANT));

                return RestaurantDto.builder()
                        .id(restaurant.getId())
                        .restaurantImage(restaurant.getImage())
                        .placeName(restaurant.getPlaceName())
                        .address(restaurant.getAddress())
                        .options(restaurant.getOptions())
                        .build();
            }).toList();
        }

        if (section.equals("review")) {
            List<Review> reviews = reviewRepository.findAllByUserId(userId);

            return reviews.stream().map(r -> {
                Restaurant restaurant = restaurantRepository.findById(r.getRestaurantId())
                        .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_RESTAURANT));

                return RestaurantDto.builder()
                        .id(restaurant.getId())
                        .restaurantImage(restaurant.getImage())
                        .placeName(restaurant.getPlaceName())
                        .address(restaurant.getAddress())
                        .options(restaurant.getOptions())
                        .build();
            }).toList();
        }

        throw new BadRequestException(ErrorCode.INVALID_DATA);
    }
}
