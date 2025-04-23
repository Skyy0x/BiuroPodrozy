package pl.sky0x.travelAgency.throwable;

public class UsernameExistsException extends RuntimeException{

    public UsernameExistsException(String message) {
        super(message);
    }
}
