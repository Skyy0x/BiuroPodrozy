package pl.sky0x.travelAgency.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.sky0x.travelAgency.controller.reuqest.PasswordRequest;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, PasswordRequest> {

    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(PasswordRequest passwordRequest, ConstraintValidatorContext context) {
        if (passwordRequest.getCurrentPassword() == null || passwordRequest.getPassword() == null) {
            return false;
        }
        return passwordRequest.getPassword().equals(passwordRequest.getReTypedPassword());
    }


}
