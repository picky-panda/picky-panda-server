package io.picky.panda.restaurant.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class RestaurantDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long restaurantId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer isAgreed;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer isDisagreed;

    @Builder
    public RestaurantDescription(Long restaurantId, String description) {
        this.restaurantId = restaurantId;
        this.description = description;
    }

    public void increaseAgree() {
        this.isAgreed++;
    }

    public void decreaseAgree() {
        this.isAgreed--;
    }

    public void increaseDisAgree() {
        this.isDisagreed++;
    }

    public void decreaseDisAgree() {
        this.isDisagreed--;
    }
}
