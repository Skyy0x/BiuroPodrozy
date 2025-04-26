package pl.sky0x.travelAgency.controller.reuqest;

import jakarta.validation.constraints.NotBlank;
import pl.sky0x.travelAgency.validation.PasswordsMatch;

@PasswordsMatch(message = "{passwordsMismatch}")
public class PasswordRequest {

    @NotBlank(message = "{currentPassword.notBlank}")
    private String currentPassword;

    @NotBlank(message = "{password.notBlank}")
    private String password;

    @NotBlank(message = "{password.notBlank}")
    private String reTypedPassword;

    public PasswordRequest() {

    }

    public PasswordRequest(String currentPassword, String password, String reTypedPassword) {
        this.currentPassword = currentPassword;
        this.password = password;
        this.reTypedPassword = reTypedPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getReTypedPassword() {
        return reTypedPassword;
    }

}
