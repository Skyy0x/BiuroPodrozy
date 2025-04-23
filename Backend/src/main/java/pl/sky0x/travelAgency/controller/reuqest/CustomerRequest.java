package pl.sky0x.travelAgency.controller.reuqest;

import pl.sky0x.travelAgency.model.travel.Gender;

import java.time.LocalDate;

public class CustomerRequest {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
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
