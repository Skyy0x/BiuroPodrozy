package pl.sky0x.travelAgency.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sky0x.travelAgency.model.user.User;
import pl.sky0x.travelAgency.repository.UserRepository;
import pl.sky0x.travelAgency.response.ResponseMessage;
import pl.sky0x.travelAgency.response.utility.ApiResponse;

@Controller
@RequestMapping("/api/manage/users")
public class ManageUserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ResponseMessage> getUsers() {
        return ApiResponse.success(User.class, userRepository.findAll());
    }

}
