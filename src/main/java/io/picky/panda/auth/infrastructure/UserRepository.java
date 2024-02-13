package io.picky.panda.auth.infrastructure;

import io.picky.panda.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // READ
    Optional<User> findBySocialId(String socialId);
    Optional<User> findByEmail(String email);

}
