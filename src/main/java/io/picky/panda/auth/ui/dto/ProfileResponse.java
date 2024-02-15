package io.picky.panda.auth.ui.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProfileResponse(

        String profileImage,
        String email,
        Integer myDescriptionCount,
        Integer myReviewCount,
        Integer mySavedRestaurantCount,
        List<RestaurantDto> recentlyEvaluatedList
) {
}
