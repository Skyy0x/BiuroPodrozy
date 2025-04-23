package pl.sky0x.travelAgency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.sky0x.travelAgency.controller.reuqest.BookTripRequest;
import pl.sky0x.travelAgency.model.user.User;
import pl.sky0x.travelAgency.response.ResponseMessage;
import pl.sky0x.travelAgency.response.utility.ApiResponse;
import pl.sky0x.travelAgency.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book/{trip}")
    public ResponseEntity<ResponseMessage> bookTrip(
            @PathVariable("trip") Long id,
            @AuthenticationPrincipal User user,
            @RequestBody BookTripRequest request) {
        if(user == null) {
            throw new BadCredentialsException("User isn't logged.");
        }

        return ApiResponse.createSuccessResponse("booking", bookingService.book(id, user, request));
    }

}
