package pl.sky0x.travelAgency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import pl.sky0x.travelAgency.controller.reuqest.BookTripRequest;
import pl.sky0x.travelAgency.controller.reuqest.CustomerRequest;
import pl.sky0x.travelAgency.model.travel.Booking;
import pl.sky0x.travelAgency.model.travel.Customer;
import pl.sky0x.travelAgency.model.travel.Trip;
import pl.sky0x.travelAgency.model.user.User;
import pl.sky0x.travelAgency.repository.BookingRepository;
import pl.sky0x.travelAgency.repository.CustomerRepository;
import pl.sky0x.travelAgency.repository.TripRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    public Booking book(Long tripId, User user, BookTripRequest request) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadCredentialsException("Trip " + tripId + " doesn't exists."));

        Booking booking = getBooking(user, trip);

        bookingRepository.save(booking);

        List<Customer> customers = new ArrayList<>();

        for (CustomerRequest requestedCustomer : request.getCustomers()) {
            Customer customer = new Customer(
                    requestedCustomer.getFirstName(),
                    requestedCustomer.getLastName(),
                    requestedCustomer.getBirthDate(),
                    requestedCustomer.getGender(),
                    booking
            );

            customers.add(customer);
        }

        customerRepository.saveAll(customers);

        booking.setCustomers(customers);
        
        return booking;
    }

    private Booking getBooking(User user, Trip trip) {
        return new Booking(
                user,
                trip
        );
    }

}
