package io.picky.panda.restaurant.ui.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record RestaurantResponse(

        Boolean isSaved,
        String image,
        String placeName,
        Double latitude,
        Double longitude,
        List<RestaurantDescriptionResponse> descriptions
) {
}
