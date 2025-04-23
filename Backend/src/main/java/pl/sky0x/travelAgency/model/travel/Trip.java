package pl.sky0x.travelAgency.model.travel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private double pricePerPerson;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean active;

    private int maxPeople;

    private String tripLeader;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Booking> bookings;

    @OneToMany(mappedBy = "trip")
    @JsonIgnore
    private List<Photo> images;

    public Trip() {

    }

    public Trip(String name, String description, double pricePerPerson, LocalDate startDate, LocalDate endDate, boolean active, int maxPeople, String tripLeader) {
        this.name = name;
        this.description = description;
        this.pricePerPerson = pricePerPerson;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
        this.maxPeople = maxPeople;
        this.tripLeader = tripLeader;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getTripLeader() {
        return tripLeader;
    }

    public void setTripLeader(String tripLeader) {
        this.tripLeader = tripLeader;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Photo> getImages() {
        return images;
    }

    public void setImages(List<Photo> images) {
        this.images = images;
    }
}
