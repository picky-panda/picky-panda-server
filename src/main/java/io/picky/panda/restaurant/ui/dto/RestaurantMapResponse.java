package io.picky.panda.restaurant.ui.dto;

import lombok.Builder;

@Builder
public record RestaurantMapResponse(
        Long id,
        String image,
        String placeName,
        Double latitude,
        Double longitude
) {
}
