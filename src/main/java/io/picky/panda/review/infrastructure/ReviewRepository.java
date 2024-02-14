package io.picky.panda.review.infrastructure;

import io.picky.panda.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // READ
    List<Review> findAllByRestaurantId(Long restaurantId);
    Boolean existsByUserIdAndRestaurantId(Long userId, Long restaurantId);
}
