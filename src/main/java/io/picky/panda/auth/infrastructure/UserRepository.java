package io.picky.panda.auth.infrastructure;

import io.picky.panda.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
