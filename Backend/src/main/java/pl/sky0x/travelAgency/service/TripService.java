package pl.sky0x.travelAgency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import pl.sky0x.travelAgency.controller.reuqest.TripRequest;
import pl.sky0x.travelAgency.model.travel.Trip;
import pl.sky0x.travelAgency.repository.TripRepository;

@Service
public class TripService {

    @Autowired
    public TripRepository tripRepository;

    public Trip createTrip(TripRequest tripRequest) {
        Trip trip = new Trip(
                tripRequest.getName(),
                tripRequest.getDescription(),
                tripRequest.getPricePerPerson(),
                tripRequest.getStartDate(),
                tripRequest.getEndDate(),
                tripRequest.isActive(),
                tripRequest.getMaxPeople(),
                tripRequest.getTripLeader()
        );

        return tripRepository.save(trip);
    }

    public Trip updateTrip(Long tripId, TripRequest tripRequest) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadCredentialsException("Trip not found with id " + tripId));

        trip.setName(tripRequest.getName());
        trip.setDescription(tripRequest.getDescription());
        trip.setPricePerPerson(tripRequest.getPricePerPerson());
        trip.setStartDate(tripRequest.getStartDate());
        trip.setEndDate(tripRequest.getEndDate());
        trip.setActive(tripRequest.isActive());
        trip.setMaxPeople(tripRequest.getMaxPeople());
        trip.setTripLeader(trip.getTripLeader());

        return tripRepository.save(trip);
    }

    public void deleteTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadCredentialsException("Trip not found with id " + tripId));

        tripRepository.delete(trip);
    }
}
