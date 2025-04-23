package pl.sky0x.travelAgency.model.travel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import pl.sky0x.travelAgency.model.user.User;

import java.util.List;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Customer> customers;

    public Booking() {

    }

    public Booking(User user, Trip trip) {
        this.user = user;
        this.trip = trip;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setCustomer(User user) {
        this.user = user;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
