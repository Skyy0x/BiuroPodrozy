package pl.sky0x.travelAgency.controller.reuqest;

public class PasswordRequest {

    private String currentPassword;
    private String password;

    public PasswordRequest() {

    }

    public PasswordRequest(String currentPassword, String password) {
        this.currentPassword = currentPassword;
        this.password = password;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getPassword() {
        return password;
    }
}
