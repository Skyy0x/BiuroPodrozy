package pl.sky0x.travelAgency.controller.reuqest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.sky0x.travelAgency.model.travel.Gender;

import java.time.LocalDate;

public class CustomerRequest {

    @NotBlank(message = "{firstName.notBlank}")
    private String firstName;

    @NotBlank(message = "{lastName.notBlank}")
    private String lastName;

    @NotBlank(message = "{birthDate.notBlank}")
    private LocalDate birthDate;

    @NotNull(message = "{gender.notNull}")
    private Gender gender;

    public CustomerRequest() {

    }

    public CustomerRequest(String firstName, String lastName, LocalDate birthDate, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }
}
