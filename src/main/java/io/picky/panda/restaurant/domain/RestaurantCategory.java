package io.picky.panda.restaurant.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RestaurantCategory {

    RESTAURANT("레스토랑")
    ;

    private final String title;
}
