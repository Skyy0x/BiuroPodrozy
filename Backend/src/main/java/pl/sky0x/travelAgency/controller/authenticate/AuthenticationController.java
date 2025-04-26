package pl.sky0x.travelAgency.controller.authenticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sky0x.travelAgency.controller.reuqest.auth.LoginRequest;
import pl.sky0x.travelAgency.controller.reuqest.auth.RegisterRequest;
import pl.sky0x.travelAgency.model.user.Token;
import pl.sky0x.travelAgency.response.ResponseMessage;
import pl.sky0x.travelAgency.response.utility.ApiResponse;
import pl.sky0x.travelAgency.service.AuthenticateService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticateService authenticateService;

    @Autowired
    public AuthenticationController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@Valid @RequestBody LoginRequest loginRequest) {
        Token token = authenticateService.login(loginRequest);
        return ApiResponse.success("token", token);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> register(@Valid @RequestBody RegisterRequest registerRequest) {
        Token token = authenticateService.register(registerRequest);
        return ApiResponse.success("token", token);
    }
}
