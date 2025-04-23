package pl.sky0x.travelAgency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import pl.sky0x.travelAgency.controller.reuqest.PasswordRequest;
import pl.sky0x.travelAgency.controller.reuqest.UserInfoRequest;
import pl.sky0x.travelAgency.model.travel.Booking;
import pl.sky0x.travelAgency.model.travel.Trip;
import pl.sky0x.travelAgency.model.user.User;
import pl.sky0x.travelAgency.repository.BookingRepository;
import pl.sky0x.travelAgency.repository.UserRepository;
import pl.sky0x.travelAgency.response.ResponseMessage;
import pl.sky0x.travelAgency.response.utility.ApiResponse;
import pl.sky0x.travelAgency.service.UserService;

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

    @GetMapping
    @RequestMapping(path = {"/role"})
    public ResponseEntity<ResponseMessage> getUserRole(
            @AuthenticationPrincipal User user
    ) {
        if(user == null) {
            throw new BadCredentialsException("User isn't logged.");
        }
        return ApiResponse.createSuccessResponse("role", user.getRole().name());
    }

    @GetMapping
    @RequestMapping(path = {"/bookings"})
    public ResponseEntity<ResponseMessage> getUserBookings(
            @AuthenticationPrincipal User user
    ) {
        if(user == null) {
            throw new BadCredentialsException("User isn't logged.");
        }

        return ApiResponse.createSuccessResponse(Booking.class, bookingRepository.findByUserId(user.getId()));
    }

    @GetMapping
    @RequestMapping(path = {"/history"})
    public ResponseEntity<ResponseMessage> getUserHistory(
            @AuthenticationPrincipal User user
    ) {
        if (user == null) {
            throw new BadCredentialsException("User isn't logged.");
        }

        List<Booking> allBookings = bookingRepository.findByUserId(user.getId());

        List<Booking> historyBookings = allBookings.stream()
                .filter(booking -> booking.getTrip().getEndDate().isBefore(LocalDate.now()))
                .toList();

        return ApiResponse.createSuccessResponse(Booking.class, historyBookings);
    }

    @DeleteMapping
    @RequestMapping(path = {"/delete"})
    public ResponseEntity<ResponseMessage> deleteUser(
            @AuthenticationPrincipal User user
    ) {
        if(user == null) {
            throw new BadCredentialsException("User isn't logged.");
        }

        Long id = userRepository.findById(user.getId())
                .map(User::getId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database."));

        return ApiResponse.createSuccessResponse("message", userService.deleteUser(id));
    }

    @GetMapping
    public ResponseEntity<ResponseMessage> getUserInfo(
            @AuthenticationPrincipal User user
    ) {
        if (user == null) {
            throw new BadCredentialsException("User isn't logged in.");
        }

        return userRepository.findById(user.getId())
                .map(foundUser -> ApiResponse.createSuccessResponse("user", foundUser))
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database."));
    }

    @PatchMapping
    @RequestMapping(path = {"/info"})
    public ResponseEntity<ResponseMessage> updateUserInfo(
            @AuthenticationPrincipal User user,
            @RequestBody UserInfoRequest request
    ) {
        if (user == null) {
            throw new BadCredentialsException("User isn't logged in.");
        }

        Long id = userRepository.findById(user.getId())
                .map(User::getId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database."));

        return ApiResponse.createSuccessResponse("user", userService.updateUserInfo(id, request));
    }

    @PatchMapping
    @RequestMapping(path = {"/password"})
    public ResponseEntity<ResponseMessage> updateUserPassword(
            @AuthenticationPrincipal User user,
            @RequestBody PasswordRequest request
    ) {
        if (user == null) {
            throw new BadCredentialsException("User isn't logged in.");
        }

        Long id = userRepository.findById(user.getId())
                .map(User::getId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database."));

        return ApiResponse.createSuccessResponse("user", userService.updateUserPassword(id, request));
    }



}
