package pl.sky0x.travelAgency.service;

import org.springframework.stereotype.Service;
import pl.sky0x.travelAgency.controller.reuqest.TripRequest;
import pl.sky0x.travelAgency.controller.reuqest.filter.TripDateRangeRequest;
import pl.sky0x.travelAgency.controller.reuqest.filter.TripDurationRangeRequest;
import pl.sky0x.travelAgency.controller.reuqest.filter.TripFilterRequest;
import pl.sky0x.travelAgency.controller.reuqest.filter.TripPriceRangeRequest;
import pl.sky0x.travelAgency.model.travel.Trip;
import pl.sky0x.travelAgency.repository.TripRepository;
import pl.sky0x.travelAgency.throwable.ResourceNotFoundException;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripService {

    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public Trip createTrip(TripRequest tripRequest) {
        Trip trip = mapToTripEntity(tripRequest);
        return tripRepository.save(trip);
    }

    public Trip updateTrip(Long tripId, TripRequest tripRequest) {
        Trip existingTrip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));

        updateTripFields(existingTrip, tripRequest);

        return tripRepository.save(existingTrip);
    }

    public void deleteTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new pl.sky0x.travelAgency.throwable.ResourceNotFoundException("Trip not found with id: " + tripId));

        tripRepository.delete(trip);
    }

    public List<Trip> getAllActiveTrips() {
        return tripRepository.findAll().stream()
                .filter(Trip::isActive)
                .toList();
    }

    public List<Trip> findTripsByFilter(TripFilterRequest request) {
        return tripRepository.findAll().stream()
                .filter(Trip::isActive)
                .filter(trip -> matchesDateRange(trip, request.getDateRangeRequest()))
                .filter(trip -> matchesPriceRange(trip, request.getPriceRangeRequest()))
                .filter(trip -> matchesDurationRange(trip, request.getDurationRangeRequest()))
                .filter(trip -> matchesPeopleNumber(trip, request.getPeopleNumber()))
                .collect(Collectors.toList());
    }

    private Trip mapToTripEntity(TripRequest request) {
        Trip trip = new Trip();
        updateTripFields(trip, request);
        return trip;
    }

    private void updateTripFields(Trip trip, TripRequest request) {
        trip.setName(request.getName());
        trip.setDescription(request.getDescription());
        trip.setPricePerPerson(request.getPricePerPerson());
        trip.setStartDate(request.getStartDate());
        trip.setEndDate(request.getEndDate());
        trip.setActive(request.isActive());
        trip.setMaxPeople(request.getMaxPeople());
        trip.setTripLeader(request.getTripLeader());
    }

    private boolean matchesDateRange(Trip trip, TripDateRangeRequest request) {
        if (request == null) return true;
        return !(trip.getEndDate().isBefore(request.getStartDate()) ||
                trip.getStartDate().isAfter(request.getEndDate()));
    }

    private boolean matchesPriceRange(Trip trip, TripPriceRangeRequest request) {
        if (request == null) return true;
        double price = trip.getPricePerPerson();
        return price >= request.getMinPrice() && price <= request.getMaxPrice();
    }

    private boolean matchesDurationRange(Trip trip, TripDurationRangeRequest request) {
        if (request == null) return true;
        long duration = ChronoUnit.DAYS.between(trip.getStartDate(), trip.getEndDate()) + 1;
        return duration >= request.getMinDays() && duration <= request.getMaxDays();
    }

    private boolean matchesPeopleNumber(Trip trip, int requestedPeople) {
        return requestedPeople <= 0 || trip.getMaxPeople() >= requestedPeople;
    }
}
