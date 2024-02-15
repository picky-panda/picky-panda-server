package io.picky.panda.restaurant.infrastructure;

import io.picky.panda.restaurant.domain.AgreedDescription;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgreedDescriptionRepository extends JpaRepository<AgreedDescription, Long> {

    // READ
    Boolean existsByUserIdAndDescriptionId(Long userId, Long descriptionId);
    Optional<AgreedDescription> findByUserIdAndDescriptionId(Long userId, Long descriptionId);
    List<AgreedDescription> findTop2ByUserId(Long userId, Pageable pageable);
    List<AgreedDescription> findAllByUserId(Long userId);

    default List<AgreedDescription>findTop2ByUserIdOrderByCreatedAtDesc(Long userId) {
        return findTop2ByUserId(userId, PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "createdAt")));
    }
}
