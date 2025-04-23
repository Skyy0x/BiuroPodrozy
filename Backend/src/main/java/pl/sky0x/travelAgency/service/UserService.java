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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public User updateUserInfo(Long id, UserInfoRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadCredentialsException("User doesn't exist."));

        if (request.getUsername() != null && !request.getUsername().equals(user.getUsername())) {
            userRepository.findByUsername(request.getUsername())
                    .ifPresent(existing -> {
                        throw new UsernameExistsException("User named " + request.getUsername() + " already exists.");
                    });
            user.setUsername(request.getUsername());
        }

        if (request.getPhone() != null) {
            user.setPhoneNumber(request.getPhone());
        }

        return userRepository.save(user);
    }

    public User updateUserPassword(Long id, PasswordRequest passwordRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadCredentialsException("User does not exist."));

        String currentPassword = passwordRequest.getCurrentPassword();
        String newPassword = passwordRequest.getPassword();

        if (!encoder.matches(currentPassword, user.getPassword())) {
            throw new BadCredentialsException("Current password is incorrect.");
        }

        if (newPassword == null || !AuthenticateService.PATTERN.matcher(newPassword).matches()) {
            throw new PasswordException("Password is too weak.");
        }

        user.setPassword(encoder.encode(newPassword));

        return userRepository.save(user);
    }

    public String deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadCredentialsException("User does not exist."));

        userRepository.delete(user);

        return "User deleted";
    }
}
