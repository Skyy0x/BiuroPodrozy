package pl.sky0x.travelAgency.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import pl.sky0x.travelAgency.controller.reuqest.BookTripRequest;
import pl.sky0x.travelAgency.model.travel.Booking;
import pl.sky0x.travelAgency.model.travel.Customer;
import pl.sky0x.travelAgency.model.travel.Trip;
import pl.sky0x.travelAgency.model.user.User;
import pl.sky0x.travelAgency.repository.BookingRepository;
import pl.sky0x.travelAgency.repository.CustomerRepository;
import pl.sky0x.travelAgency.repository.TripRepository;

import java.util.List;

@Service
public class BookingService {

    private static final String TRIP_NOT_FOUND_MESSAGE = "Trip with id %d does not exist.";

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Booking book(Long tripId, User user, BookTripRequest request) {
        Trip trip = findTripById(tripId);
        Booking booking = createBooking(user, trip);

        bookingRepository.save(booking);

        List<Customer> customers = createCustomersFromRequest(request, booking);
        customerRepository.saveAll(customers);

        booking.setCustomers(customers);
        return booking;
    }

    private Trip findTripById(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new BadCredentialsException(
                        String.format(TRIP_NOT_FOUND_MESSAGE, tripId)));
    }

    private Booking createBooking(User user, Trip trip) {
        return new Booking(user, trip);
    }

    private List<Customer> createCustomersFromRequest(BookTripRequest request, Booking booking) {
        return request.getCustomers().stream()
                .map(customerRequest -> new Customer(
                        customerRequest.getFirstName(),
                        customerRequest.getLastName(),
                        customerRequest.getBirthDate(),
                        customerRequest.getGender(),
                        booking
                ))
                .toList();
    }
}