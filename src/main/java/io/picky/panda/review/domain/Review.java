package io.picky.panda.review.domain;

import io.picky.panda.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long restaurantId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String content;

    @Builder
    public Review(Long restaurantId, Long userId, String content) {
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.content = content;
    }
}
