package io.picky.panda.restaurant.ui.dto;

import lombok.Builder;

@Builder
public record RestaurantDescriptionResponse(

        String description,
        Integer isAgreed,
        Integer isDisagreed
) {
}
