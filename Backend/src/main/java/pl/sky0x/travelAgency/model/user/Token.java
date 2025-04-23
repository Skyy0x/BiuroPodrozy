package pl.sky0x.travelAgency.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(unique = true)
    public String token;

    public boolean expired = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    public User user;

    public Token(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public Token() {

    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public boolean isExpired() {
        return expired;
    }

    public User getUser() {
        return user;
    }
}
