package pl.sky0x.travelAgency.controller.reuqest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserInfoRequest {

    @NotBlank(message = "{username.notBlank}")
    @Size(min = 3, max = 50, message = "{username.size}")
    private String username;

    @NotBlank(message = "{phone.notBlank}")
    @Size(min = 6, max = 15, message = "{phone.size}")
    private String phone;

    public UserInfoRequest() {

    }

    public UserInfoRequest(String username, String phone) {
        this.username = username;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

}
