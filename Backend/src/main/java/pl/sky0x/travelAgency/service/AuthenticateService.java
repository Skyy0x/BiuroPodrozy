package pl.sky0x.travelAgency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sky0x.travelAgency.controller.reuqest.auth.LoginRequest;
import pl.sky0x.travelAgency.controller.reuqest.auth.RegisterRequest;
import pl.sky0x.travelAgency.model.user.Role;
import pl.sky0x.travelAgency.model.user.Token;
import pl.sky0x.travelAgency.model.user.User;
import pl.sky0x.travelAgency.repository.TokenRepository;
import pl.sky0x.travelAgency.repository.UserRepository;
import pl.sky0x.travelAgency.throwable.PasswordException;
import pl.sky0x.travelAgency.throwable.UsernameExistsException;

import java.util.regex.Pattern;

@Service
public class AuthenticateService {

    public static final Pattern PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~!@#$%^&*])[a-zA-Z][a-zA-Z\\d]*[~!@#$%^&*?<>]*$");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtService service;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Token register(RegisterRequest registerRequest) {
        if(userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UsernameExistsException("User named " + registerRequest.getUsername() + " exists.");
        }

        if(!PATTERN.matcher(registerRequest.getPassword()).find()) {
            throw new PasswordException("Password is to weak.");
        }

        User user = new User(
                registerRequest.getUsername(),
                encoder.encode(registerRequest.getPassword()),
                registerRequest.getEmail(),
                registerRequest.getPhone(),
                registerRequest.getCountry()
        );

        userRepository.save(user);

        Token token = new Token(
                service.generateToken(user),
                user
        );

        return tokenRepository.save(token);
    }

    public Token register(String username, String password) {
        if(userRepository.findByUsername(username).isPresent()) {
            throw new UsernameExistsException("User named " + username + " exists.");
        }

        if(!PATTERN.matcher(password).find()) {
            throw new PasswordException("Password is to weak.");
        }

        User user = new User(
                username,
                encoder.encode(password),
                Role.ADMIN
        );

        userRepository.save(user);

        Token token = new Token(
                service.generateToken(user),
                user
        );

        return tokenRepository.save(token);
    }

    public Token login(LoginRequest request) {
        final User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not founded."));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        final Token token = new Token(
                service.generateToken(user),
                user
        );

        tokenRepository.save(token);

        return token;
    }

}