package pl.sky0x.travelAgency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sky0x.travelAgency.model.travel.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

}
