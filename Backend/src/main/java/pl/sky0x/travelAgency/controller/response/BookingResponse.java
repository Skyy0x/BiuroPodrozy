package pl.sky0x.travelAgency.controller.response;

import pl.sky0x.travelAgency.model.travel.Customer;

import java.util.List;

public class BookingResponse {
    private Long id;
    private String username;
    private List<Customer> customers;

    public BookingResponse(Long id, String username, List<Customer> customers) {
        this.id = id;
        this.username = username;
        this.customers = customers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}

