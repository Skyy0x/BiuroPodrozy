package pl.sky0x.travelAgency.controller.reuqest;

import java.time.LocalDate;

public class TripRequest {

    private String name;
    private String description;
    private double pricePerPerson;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
    private int maxPeople;
    private String tripLeader;

    public TripRequest() {

    }

    public TripRequest(String name, String description, double pricePerPerson, LocalDate startDate, LocalDate endDate, boolean active, int maxPeople, String tripLeader) {
        this.name = name;
        this.description = description;
        this.pricePerPerson = pricePerPerson;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
        this.maxPeople = maxPeople;
        this.tripLeader = tripLeader;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPricePerPerson() {
        return pricePerPerson;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isActive() {
        return active;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public String getTripLeader() {
        return tripLeader;
    }
}
