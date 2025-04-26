package pl.sky0x.travelAgency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import pl.sky0x.travelAgency.controller.reuqest.PasswordRequest;
import pl.sky0x.travelAgency.controller.reuqest.UserInfoRequest;
import pl.sky0x.travelAgency.model.travel.Booking;
import pl.sky0x.travelAgency.model.user.User;
import pl.sky0x.travelAgency.repository.BookingRepository;
import pl.sky0x.travelAgency.repository.UserRepository;
import pl.sky0x.travelAgency.response.ResponseMessage;
import pl.sky0x.travelAgency.response.utility.ApiResponse;
import pl.sky0x.travelAgency.service.UserService;
import pl.sky0x.travelAgency.validation.ValidateUser;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/role")
    @ValidateUser
    public ResponseEntity<ResponseMessage> getUserRole(@AuthenticationPrincipal User user) {
        return ApiResponse.success("role", user.getRole().name());
    }

    @GetMapping("/bookings")
    @ValidateUser
    public ResponseEntity<ResponseMessage> getUserBookings(@AuthenticationPrincipal User user) {
        List<Booking> bookings = bookingRepository.findByUserId(user.getId());
        return ApiResponse.success(Booking.class, bookings);
    }

    @GetMapping("/history")
    @ValidateUser
    public ResponseEntity<ResponseMessage> getUserBookingHistory(@AuthenticationPrincipal User user) {
        List<Booking> history = bookingRepository.findByUserId(user.getId()).stream()
                .filter(booking -> booking.getTrip().getEndDate().isBefore(LocalDate.now()))
                .toList();
        return ApiResponse.success(Booking.class, history);
    }

    @DeleteMapping("/delete")
    @ValidateUser
    public ResponseEntity<ResponseMessage> deleteUserAccount(@AuthenticationPrincipal User user) {
        Long userId = getUserId(user);
        return ApiResponse.success("message", userService.deleteUser(userId));
    }

    @GetMapping
    @ValidateUser
    public ResponseEntity<ResponseMessage> getUserInfo(@AuthenticationPrincipal User user) {
        return userRepository.findById(user.getId())
                .map(foundUser -> ApiResponse.success("user", foundUser))
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database."));
    }

    @PatchMapping("/info")
    @ValidateUser
    public ResponseEntity<ResponseMessage> updateUserInfo(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UserInfoRequest request
    ) {
        Long userId = getUserId(user);
        return ApiResponse.success("user", userService.updateUserInfo(userId, request));
    }

    @PatchMapping("/password")
    @ValidateUser
    public ResponseEntity<ResponseMessage> updateUserPassword(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody PasswordRequest request
    ) {
        Long userId = getUserId(user);
        return ApiResponse.success("user", userService.updateUserPassword(userId, request));
    }

    private Long getUserId(User user) {
        return userRepository.findById(user.getId())
                .map(User::getId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database."));
    }

}
