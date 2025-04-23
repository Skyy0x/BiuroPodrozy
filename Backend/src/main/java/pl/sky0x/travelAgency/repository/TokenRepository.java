package pl.sky0x.travelAgency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sky0x.travelAgency.model.user.Token;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

}
