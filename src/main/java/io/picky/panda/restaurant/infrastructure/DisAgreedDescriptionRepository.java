package io.picky.panda.restaurant.infrastructure;

import io.picky.panda.restaurant.domain.DisAgreedDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DisAgreedDescriptionRepository extends JpaRepository<DisAgreedDescription, Long> {

    // READ
    Boolean existsByUserIdAndDescriptionId(Long userId, Long descriptionId);
    Optional<DisAgreedDescription> findByUserIdAndDescriptionId(Long userId, Long descriptionId);
}
