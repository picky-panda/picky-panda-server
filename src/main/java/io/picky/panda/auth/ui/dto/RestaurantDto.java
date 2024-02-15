package io.picky.panda.auth.ui.dto;

import lombok.Builder;

@Builder
public record RestaurantDto(

        Long id,
        String restaurantImage,
        String placeName,
        String address,
        String options
) {
}
