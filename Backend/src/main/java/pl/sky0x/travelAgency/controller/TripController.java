package pl.sky0x.travelAgency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sky0x.travelAgency.controller.reuqest.filter.TripDateRangeRequest;
import pl.sky0x.travelAgency.controller.reuqest.filter.TripDurationRangeRequest;
import pl.sky0x.travelAgency.controller.reuqest.filter.TripFilterRequest;
import pl.sky0x.travelAgency.controller.reuqest.filter.TripPriceRangeRequest;
import pl.sky0x.travelAgency.model.travel.Trip;
import pl.sky0x.travelAgency.repository.TripRepository;
import pl.sky0x.travelAgency.response.ResponseMessage;
import pl.sky0x.travelAgency.response.utility.ApiResponse;

import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    @GetMapping
    public ResponseEntity<ResponseMessage> getTrips() {
        return ApiResponse.createSuccessResponse(Trip.class, tripRepository.findAll()
                .stream()
                .filter(Trip::isActive)
                .toList());
    }

    @PostMapping()
    @RequestMapping(path = {"/search"})
    public ResponseEntity<ResponseMessage> getTripsByDate(@RequestBody TripFilterRequest request) {
        List<Trip> filteredTrips = tripRepository.findAll();
        if(request.getDateRangeRequest() != null) {
            filteredTrips = filterTrips(filteredTrips, request.getDateRangeRequest());
        }

        if(request.getPriceRangeRequest() != null) {
            filteredTrips = filterTrips(filteredTrips, request.getPriceRangeRequest());
        }

        if(request.getDurationRangeRequest() != null) {
            filteredTrips = filterTrips(filteredTrips, request.getDurationRangeRequest());
        }

        if(request.getPeopleNumber() > 0) {
            filteredTrips = filterTrips(filteredTrips, request.getPeopleNumber());
        }

        return ApiResponse.createSuccessResponse(Trip.class, filteredTrips);
    }

    private List<Trip> filterTrips(List<Trip> trips, TripDateRangeRequest request) {
        return trips.stream()
                .filter(trip ->
                        !(trip.getEndDate().isBefore(request.getStartDate()) ||
                                trip.getStartDate().isAfter(request.getEndDate()))
                )
                .toList();
    }

    private List<Trip> filterTrips(List<Trip> trips, TripPriceRangeRequest request) {
        return trips.stream()
                .filter(trip ->
                        trip.getPricePerPerson() >= request.getMinPrice() &&
                                trip.getPricePerPerson() <= request.getMaxPrice()
                )
                .toList();
    }

    private List<Trip> filterTrips(List<Trip> trips, TripDurationRangeRequest request) {
        return trips.stream()
                .filter(trip -> {
                    long duration = ChronoUnit.DAYS.between(trip.getStartDate(), trip.getEndDate()) + 1; // inclusive
                    return duration >= request.getMinDays() &&
                            duration <= request.getMaxDays();
                })
                .toList();
    }

    private List<Trip> filterTrips(List<Trip> trips, int peopleNumber) {
        return trips.stream()
                .filter(trip -> trip.getMaxPeople() <= peopleNumber)
                .toList();
    }

}
