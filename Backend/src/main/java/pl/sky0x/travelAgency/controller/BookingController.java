package pl.sky0x.travelAgency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.sky0x.travelAgency.controller.reuqest.BookTripRequest;
import pl.sky0x.travelAgency.model.user.User;
import pl.sky0x.travelAgency.response.ResponseMessage;
import pl.sky0x.travelAgency.response.utility.ApiResponse;
import pl.sky0x.travelAgency.service.BookingService;
import pl.sky0x.travelAgency.validation.ValidateUser;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book/{trip}")
    @ValidateUser
    public ResponseEntity<ResponseMessage> bookTrip(
            @PathVariable("trip") Long id,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody BookTripRequest request) {
        return ApiResponse.success("booking", bookingService.book(id, user, request));
    }

}
