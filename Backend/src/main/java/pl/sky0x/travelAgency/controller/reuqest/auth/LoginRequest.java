package pl.sky0x.travelAgency.controller.reuqest.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "{username.notBlank}")
    @Size(min = 3, max = 50, message = "{username.size}")
    private String username;

    @NotBlank(message = "{password.notBlank}")
    @Size(min = 6, message = "{password.size}")
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequest() {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
