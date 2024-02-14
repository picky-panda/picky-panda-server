package io.picky.panda.restaurant.domain;

import io.picky.panda.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgreedDescription extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long descriptionId;

    @Builder
    public AgreedDescription(Long userId, Long descriptionId) {
        this.userId = userId;
        this.descriptionId = descriptionId;
    }
}
