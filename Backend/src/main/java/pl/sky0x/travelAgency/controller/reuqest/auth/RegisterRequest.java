package pl.sky0x.travelAgency.controller.reuqest.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import pl.sky0x.travelAgency.validation.ValidCountry;

public class RegisterRequest {

    @NotBlank(message = "{username.notBlank}")
    @Size(min = 3, max = 50, message = "{username.size}")
    private String username;

    @NotBlank(message = "{password.notBlank}")
    @Size(min = 6, message = "{password.size}")
    private String password;

    @NotBlank(message = "{country.notBlank}")
    @ValidCountry(message = "{country.invalid}")
    private String country;

    @NotBlank(message = "{email.notBlank}")
    @Email(message = "{email.invalid}")
    private String email;

    @NotBlank(message = "{phone.notBlank}")
    @Size(min = 6, max = 15, message = "{phone.size}")
    private String phone;

    public RegisterRequest() {

    }

    public RegisterRequest(String username, String password, String country, String email, String phone) {
        this.username = username;
        this.password = password;
        this.country = country;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
