package pl.sky0x.travelAgency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sky0x.travelAgency.controller.reuqest.PasswordRequest;
import pl.sky0x.travelAgency.controller.reuqest.UserInfoRequest;
import pl.sky0x.travelAgency.model.user.User;
import pl.sky0x.travelAgency.repository.UserRepository;
import pl.sky0x.travelAgency.throwable.PasswordException;
import pl.sky0x.travelAgency.throwable.UsernameExistsException;

@Service
public class UserService {

    private static final String USER_NOT_FOUND_MESSAGE = "User does not exist.";
    private static final String INCORRECT_PASSWORD_MESSAGE = "Current password is incorrect.";
    private static final String WEAK_PASSWORD_MESSAGE = "Password is too weak.";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User updateUserInfo(Long userId, UserInfoRequest request) {
        User user = getUserById(userId);

        updateUsernameIfChanged(user, request.getUsername());
        updatePhoneNumberIfPresent(user, request.getPhone());

        return userRepository.save(user);
    }

    public User updateUserPassword(Long userId, PasswordRequest request) {
        User user = getUserById(userId);

        validateCurrentPassword(user, request.getCurrentPassword());
        validateNewPasswordStrength(request.getPassword());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }

    public String deleteUser(Long userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
        return "User deleted";
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BadCredentialsException(USER_NOT_FOUND_MESSAGE));
    }

    private void updateUsernameIfChanged(User user, String newUsername) {
        if (newUsername != null && !newUsername.equals(user.getUsername())) {
            userRepository.findByUsername(newUsername)
                    .ifPresent(existing -> {
                        throw new UsernameExistsException("User named " + newUsername + " already exists.");
                    });
            user.setUsername(newUsername);
        }
    }

    private void updatePhoneNumberIfPresent(User user, String phoneNumber) {
        if (phoneNumber != null) {
            user.setPhoneNumber(phoneNumber);
        }
    }

    private void validateCurrentPassword(User user, String currentPassword) {
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BadCredentialsException(INCORRECT_PASSWORD_MESSAGE);
        }
    }

    private void validateNewPasswordStrength(String newPassword) {
        if (newPassword == null || !AuthenticateService.PATTERN.matcher(newPassword).matches()) {
            throw new PasswordException(WEAK_PASSWORD_MESSAGE);
        }
    }
}
