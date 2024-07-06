package mtn.momo.contract.repayment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mtn.momo.contract.repayment.exception.UserAlreadyExistException;
import mtn.momo.contract.repayment.model.UserDto;
import mtn.momo.contract.repayment.service.UserService;
import mtn.momo.contract.repayment.service.repository.UserRepository;
import mtn.momo.contract.repayment.service.repository.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerNewUser(UserDto userDto) throws UserAlreadyExistException {
        Optional<User> user = userRepository.findByUsername(userDto.getUsername());

        if (user.isPresent()) {
            log.error("User {} already exists", userDto.getUsername());
            throw new UserAlreadyExistException("User name already exists");
        }

        User newUser = User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();

        log.info("Creating new user");
        return mapUser(userRepository.save(newUser));
    }

    private UserDto mapUser(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .surname(user.getSurname())
                .name(user.getName())
                .build();
    }
}
