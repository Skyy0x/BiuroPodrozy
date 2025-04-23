package pl.sky0x.travelAgency.controller.reuqest;

public class UserInfoRequest {

    private String username;
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
