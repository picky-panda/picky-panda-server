package io.picky.panda.restaurant.infrastructure;

import io.picky.panda.restaurant.domain.AgreedDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgreedDescriptionRepository extends JpaRepository<AgreedDescription, Long> {

    // READ
    Boolean existsByUserIdAndDescriptionId(Long userId, Long descriptionId);
    Optional<AgreedDescription> findByUserIdAndDescriptionId(Long userId, Long descriptionId);
}
