package pl.sky0x.travelAgency.controller.reuqest.auth;

public class RegisterRequest {

    private String username;
    private String password;
    private String country;
    private String email;
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
