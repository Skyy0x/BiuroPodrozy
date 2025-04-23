package pl.sky0x.travelAgency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sky0x.travelAgency.model.travel.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
