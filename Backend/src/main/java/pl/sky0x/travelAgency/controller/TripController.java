package pl.sky0x.travelAgency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sky0x.travelAgency.controller.reuqest.filter.TripFilterRequest;
import pl.sky0x.travelAgency.model.travel.Trip;
import pl.sky0x.travelAgency.response.ResponseMessage;
import pl.sky0x.travelAgency.response.utility.ApiResponse;
import pl.sky0x.travelAgency.service.TripService;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @GetMapping
    public ResponseEntity<ResponseMessage> getAllActiveTrips() {
        return ApiResponse.success(Trip.class, tripService.getAllActiveTrips());
    }

    @PostMapping("/search")
    public ResponseEntity<ResponseMessage> getFilteredTrips(@RequestBody TripFilterRequest request) {
        return ApiResponse.success(Trip.class, tripService.findTripsByFilter(request));
    }

}
