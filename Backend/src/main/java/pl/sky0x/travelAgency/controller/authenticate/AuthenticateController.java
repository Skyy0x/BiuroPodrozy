package pl.sky0x.travelAgency.controller.authenticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sky0x.travelAgency.controller.reuqest.auth.LoginRequest;
import pl.sky0x.travelAgency.controller.reuqest.auth.RegisterRequest;
import pl.sky0x.travelAgency.model.user.Token;
import pl.sky0x.travelAgency.response.ResponseMessage;
import pl.sky0x.travelAgency.response.utility.ApiResponse;
import pl.sky0x.travelAgency.service.AuthenticateService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticateController {

    @Autowired
    private AuthenticateService authenticateService;

    @PostMapping()
    @RequestMapping(path = {"/login"})
    public ResponseEntity<ResponseMessage> login(@RequestBody LoginRequest loginRequest) {
        final Token token = authenticateService.login(loginRequest);

        return ApiResponse.createSuccessResponse("token", token);
    }

    @PostMapping()
    @RequestMapping(path = {"/register"})
    public ResponseEntity<ResponseMessage> register(@RequestBody RegisterRequest registerRequest) {
        final Token token = authenticateService.register(registerRequest);

        return ApiResponse.createSuccessResponse("token", token);
    }

}
