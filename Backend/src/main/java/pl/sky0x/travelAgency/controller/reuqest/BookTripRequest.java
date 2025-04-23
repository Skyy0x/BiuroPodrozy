package pl.sky0x.travelAgency.controller.reuqest;

import java.util.Collection;

public class BookTripRequest {

    private Collection<CustomerRequest> customers;

    public BookTripRequest() {

    }

    public BookTripRequest(Collection<CustomerRequest> customers) {
        this.customers = customers;
    }

    public Collection<CustomerRequest> getCustomers() {
        return customers;
    }

    public void setCustomers(Collection<CustomerRequest> customers) {
        this.customers = customers;
    }
}
