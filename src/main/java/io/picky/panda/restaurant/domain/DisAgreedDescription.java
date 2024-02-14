package io.picky.panda.restaurant.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DisAgreedDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long descriptionId;

    @Builder
    public DisAgreedDescription(Long userId, Long descriptionId) {
        this.userId = userId;
        this.descriptionId = descriptionId;
    }
}
