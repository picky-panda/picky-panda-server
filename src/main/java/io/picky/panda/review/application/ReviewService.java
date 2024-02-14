package io.picky.panda.review.application;

import io.picky.panda.auth.application.UserServiceUtils;
import io.picky.panda.auth.infrastructure.UserRepository;
import io.picky.panda.exception.ErrorCode;
import io.picky.panda.exception.model.ForbiddenException;
import io.picky.panda.exception.model.NotFoundException;
import io.picky.panda.restaurant.application.RestaurantServiceUtils;
import io.picky.panda.restaurant.infrastructure.RestaurantRepository;
import io.picky.panda.review.domain.Review;
import io.picky.panda.review.infrastructure.ReviewRepository;
import io.picky.panda.review.ui.dto.ReviewResponse;
import io.picky.panda.review.ui.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void registerReview(Long userId, Long restaurantId, String content) {

        UserServiceUtils.existsUserById(userRepository, userId);
        RestaurantServiceUtils.isExistsRestaurant(restaurantRepository, restaurantId);

        reviewRepository.save(Review.builder()
                .userId(userId)
                .restaurantId(restaurantId)
                .content(content)
                .build()
        );
    }

    @Transactional
    public ReviewResponse getAllReview(Long userId, Long restaurantId) {

        UserServiceUtils.existsUserById(userRepository, userId);
        RestaurantServiceUtils.isExistsRestaurant(restaurantRepository, restaurantId);

        List<ReviewResponseDto> reviewResponseDtos = reviewRepository.findAllByRestaurantId(restaurantId).stream().map(review ->
                ReviewResponseDto.builder()
                        .id(review.getId())
                        .writerId(review.getUserId())
                        .content(review.getContent())
                        .createdAt(review.getCreatedAt())
                        .build()
        ).toList();

        return ReviewResponse.builder()
                .userId(userId)
                .reviewList(reviewResponseDtos)
                .build();
    }

    @Transactional
    public void deleteReview(Long userId, Long restaurantId, Long reviewId) {

        UserServiceUtils.existsUserById(userRepository, userId);
        RestaurantServiceUtils.isExistsRestaurant(restaurantRepository, restaurantId);
        if (!reviewRepository.existsByUserIdAndRestaurantId(userId, restaurantId)) {
            throw new NotFoundException(ErrorCode.NOT_EXISTS_REVIEW);
        }

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_REVIEW));

        if (!review.getUserId().equals(userId)) {
            throw new ForbiddenException(ErrorCode.FORBIDDEN_REVIEW);
        }

        reviewRepository.delete(review);
    }
}
