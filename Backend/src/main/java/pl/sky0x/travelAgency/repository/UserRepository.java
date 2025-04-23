package pl.sky0x.travelAgency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sky0x.travelAgency.model.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
