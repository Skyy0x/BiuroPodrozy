package pl.sky0x.travelAgency.controller.reuqest;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

public class BookTripRequest {

    @NotEmpty(message = "{customers.notEmpty}")
    @Valid
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
