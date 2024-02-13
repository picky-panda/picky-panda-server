package io.picky.panda.auth.application;

import io.picky.panda.auth.infrastructure.UserRepository;
import io.picky.panda.exception.ErrorCode;
import io.picky.panda.exception.model.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceUtils {

    public static void existsUserById(UserRepository userRepository, Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(ErrorCode.UNREGISTERED_USER);
        }
    }
}
