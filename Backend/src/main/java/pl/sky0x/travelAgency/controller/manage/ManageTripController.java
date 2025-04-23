package pl.sky0x.travelAgency.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import pl.sky0x.travelAgency.controller.response.BookingResponse;
import pl.sky0x.travelAgency.controller.reuqest.TripRequest;
import pl.sky0x.travelAgency.model.travel.Booking;
import pl.sky0x.travelAgency.model.travel.Trip;
import pl.sky0x.travelAgency.repository.BookingRepository;
import pl.sky0x.travelAgency.repository.TripRepository;
import pl.sky0x.travelAgency.response.ResponseMessage;
import pl.sky0x.travelAgency.response.utility.ApiResponse;
import pl.sky0x.travelAgency.service.TripService;

import java.util.List;

@RestController
@RequestMapping("/api/manage/trips")
public class ManageTripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping
    public ResponseEntity<ResponseMessage> getTrips() {
        return ApiResponse.createSuccessResponse(Trip.class, tripRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getBookingsByTrip(@PathVariable("id") Long id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new BadCredentialsException("Trip doesn't exist."));

        List<BookingResponse> responses = bookingRepository.findAll()
                .stream()
                .filter(booking -> booking.getTrip().getId().equals(trip.getId()))
                .map(booking -> new BookingResponse(
                        booking.getId(),
                        booking.getUser().getUsername(),
                        booking.getCustomers()
                ))
                .toList();

        return ApiResponse.createSuccessResponse(Booking.class, responses);
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> createTrip(@RequestBody TripRequest tripRequest) {
        return ApiResponse.createSuccessResponse(Trip.class, tripService.createTrip(tripRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateTrip(@PathVariable("id") Long id, @RequestBody TripRequest tripRequest) {
        return ApiResponse.createSuccessResponse(Trip.class, tripService.updateTrip(id, tripRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteTrip(@PathVariable("id") Long id) {
        tripService.deleteTrip(id);
        return ApiResponse.createSuccessResponse("message", "Trip has been deleted.");
    }
}
